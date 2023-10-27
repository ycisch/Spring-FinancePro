<template>
    <div class="app-container">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="对应的当前记录信息" prop="financeId">
          <el-input
            v-model="queryParams.financeId"
            placeholder="请输入对应的当前记录信息"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="结账时间" prop="recordTime">
          <el-date-picker clearable
            v-model="queryParams.recordTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择结账时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结账金额" prop="recordMoney">
          <el-input
            v-model="queryParams.recordMoney"
            placeholder="请输入结账金额"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="操作人" prop="operator">
          <el-input
            v-model="queryParams.operator"
            placeholder="请输入操作人"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
  
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
            v-hasPermi="['system:record:add']"
          >新增</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="success"
            plain
            icon="el-icon-edit"
            size="mini"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['system:record:edit']"
          >修改</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            plain
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['system:record:remove']"
          >删除</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="warning"
            plain
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['system:record:export']"
          >导出</el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
  
      <el-table v-loading="loading" :data="recordList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="自增id" align="center" prop="id" />
        <el-table-column label="对应的当前记录信息" align="center" prop="financeId" />
        <el-table-column label="结账时间" align="center" prop="recordTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.recordTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="结账金额" align="center" prop="recordMoney" />
        <el-table-column label="操作人" align="center" prop="operator" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['system:record:edit']"
            >修改</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['system:record:remove']"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
  
      <!-- 添加或修改结账记录格对话框 -->
      <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <el-form-item label="对应的当前记录信息" prop="financeId">
            <el-input v-model="form.financeId" placeholder="请输入对应的当前记录信息" />
          </el-form-item>
          <el-form-item label="结账时间" prop="recordTime">
            <el-date-picker clearable
              v-model="form.recordTime"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="请选择结账时间">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="结账金额" prop="recordMoney">
            <el-input v-model="form.recordMoney" placeholder="请输入结账金额" />
          </el-form-item>
          <el-form-item label="操作人" prop="operator">
            <el-input v-model="form.operator" placeholder="请输入操作人" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>
    </div>
  </template>
  
  <script>
  import { listRecord, getRecord, delRecord, addRecord, updateRecord } from "@/api/system/record";
  
  export default {
    name: "Record",
    data() {
      return {
        // 遮罩层
        loading: true,
        // 选中数组
        ids: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        // 显示搜索条件
        showSearch: true,
        // 总条数
        total: 0,
        // 结账记录格表格数据
        recordList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          financeId: null,
          recordTime: null,
          recordMoney: null,
          operator: null
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          recordTime: [
            { required: true, message: "结账时间不能为空", trigger: "blur" }
          ],
          recordMoney: [
            { required: true, message: "结账金额不能为空", trigger: "blur" }
          ],
        }
      };
    },
    created() {
      this.getList();
    },
    methods: {
      /** 查询结账记录格列表 */
      getList() {
        this.loading = true;
        listRecord(this.queryParams).then(response => {
          this.recordList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          id: null,
          financeId: null,
          recordTime: null,
          recordMoney: null,
          operator: null
        };
        this.resetForm("form");
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.resetForm("queryForm");
        this.handleQuery();
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.id)
        this.single = selection.length!==1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加结账记录格";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getRecord(id).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改结账记录格";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateRecord(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addRecord(this.form).then(response => {
                this.$modal.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              });
            }
          }
        });
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const ids = row.id || this.ids;
        this.$modal.confirm('是否确认删除结账记录格编号为"' + ids + '"的数据项？').then(function() {
          return delRecord(ids);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('system/record/export', {
          ...this.queryParams
        }, `record_${new Date().getTime()}.xlsx`)
      }
    }
  };
  </script>
  
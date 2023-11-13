<template>
    <div class="app-container">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="对应的typeid" prop="infoTypeId">
          <el-input
            v-model="queryParams.infoTypeId"
            placeholder="请输入对应的typeid"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="其他类型名称" prop="infoName">
          <el-input
            v-model="queryParams.infoName"
            placeholder="请输入其他类型名称"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="排序" prop="infoSort">
          <el-input
            v-model="queryParams.infoSort"
            placeholder="请输入排序"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="是否删除" prop="deleted">
          <el-input
            v-model="queryParams.deleted"
            placeholder="请输入是否删除"
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
            v-hasPermi="['system:info:add']"
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
            v-hasPermi="['system:info:edit']"
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
            v-hasPermi="['system:info:remove']"
          >删除</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="warning"
            plain
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['system:info:export']"
          >导出</el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
  
      <el-table v-loading="loading" :data="infoList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="其他类型id" align="center" prop="infoId" />
        <el-table-column label="对应的typeid" align="center" prop="infoTypeId" />
        <el-table-column label="类型(文本 选择框等)" align="center" prop="infoType" />
        <el-table-column label="其他类型名称" align="center" prop="infoName" />
        <el-table-column label="排序" align="center" prop="infoSort" />
        <el-table-column label="是否删除" align="center" prop="deleted" />
        <el-table-column label="操作人" align="center" prop="operator" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['system:info:edit']"
            >修改</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['system:info:remove']"
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
  
      <!-- 添加或修改类型的其他字段对话框 -->
      <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <el-form-item label="对应的typeid" prop="infoTypeId">
            <el-input v-model="form.infoTypeId" placeholder="请输入对应的typeid" />
          </el-form-item>
          <el-form-item label="其他类型名称" prop="infoName">
            <el-input v-model="form.infoName" placeholder="请输入其他类型名称" />
          </el-form-item>
          <el-form-item label="排序" prop="infoSort">
            <el-input v-model="form.infoSort" placeholder="请输入排序" />
          </el-form-item>
          <el-form-item label="是否删除" prop="deleted">
            <el-input v-model="form.deleted" placeholder="请输入是否删除" />
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
  import { listInfo, getInfo, delInfo, addInfo, updateInfo } from "@/api/system/info";
  
  export default {
    name: "Info",
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
        // 类型的其他字段表格数据
        infoList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          infoTypeId: null,
          infoType: null,
          infoName: null,
          infoSort: null,
          deleted: null,
          operator: null,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
        }
      };
    },
    created() {
      this.getList();
    },
    methods: {
      /** 查询类型的其他字段列表 */
      getList() {
        this.loading = true;
        listInfo(this.queryParams).then(response => {
          this.infoList = response.rows;
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
          infoId: null,
          infoTypeId: null,
          infoType: null,
          infoName: null,
          infoSort: null,
          deleted: null,
          operator: null,
          createTime: null,
          updateTime: null
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
        this.ids = selection.map(item => item.infoId)
        this.single = selection.length!==1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加类型的其他字段";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const infoId = row.infoId || this.ids
        getInfo(infoId).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改类型的其他字段";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.infoId != null) {
              updateInfo(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addInfo(this.form).then(response => {
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
        const infoIds = row.infoId || this.ids;
        this.$modal.confirm('是否确认删除类型的其他字段编号为"' + infoIds + '"的数据项？').then(function() {
          return delInfo(infoIds);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('system/info/export', {
          ...this.queryParams
        }, `info_${new Date().getTime()}.xlsx`)
      }
    }
  };
  </script>
  
<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="表格名称" prop="typeName">
        <el-input v-model="queryParams.typeName" placeholder="请输入表格名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <!-- <el-form-item label="创建时间" prop="typeTime">
          <el-date-picker clearable
            v-model="queryParams.typeTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择创建时间">
          </el-date-picker>
        </el-form-item> -->
      <el-form-item label="表格描述" prop="typeInfo">
        <el-input v-model="queryParams.typeInfo" placeholder="请输入表格描述" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['system:type:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['system:type:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['system:type:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['system:type:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="typeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="表格名称" align="center" prop="typeName" />
      <el-table-column label="表格描述" align="center" prop="typeInfo" />
      <el-table-column label="创建时间" align="center" prop="typeTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.typeTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>

      <!-- <el-table-column label="合作状态" align="center" prop="typeFlag">
        <template slot-scope="scope">
          <el-tag :type="returnFlagInfo(scope.row.typeFlag)">{{returnFlag(scope.row.typeFlag)}}</el-tag>
        </template>
      </el-table-column> -->
      <el-table-column label="基础数据" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-search" @click="handleShow(scope.row)"
            v-hasPermi="['system:type:edit']">查看</el-button>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['system:type:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['system:type:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改格信息用于保存多个格 每个格都有自己的名称 类似于Excel对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body :before-close="resetInfo">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="表格名称" prop="typeName">
          <el-input v-model="form.typeName" placeholder="请输入表格名称" />
          <!-- <el-col :span="8">
            <el-input v-model="form.typeName" placeholder="请输入表格名称" />
          </el-col> -->
        </el-form-item>
        <el-form-item label="表格描述" prop="typeInfo">
          <el-input v-model="form.typeInfo" placeholder="请输入表格描述" />
          <!-- <el-col :span="8">
            <el-input v-model="form.typeInfo" placeholder="请输入表格描述" />
          </el-col> -->
        </el-form-item>
        <el-form-item label="合作状态" prop="typeFlag">
          <el-radio v-model="form.typeFlag" label="1">合作中</el-radio>
          <el-radio v-model="form.typeFlag" label="2">未合作</el-radio>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>

      <div v-show="isCooperating">
        <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddDetails">添加</el-button>
        <el-button type="error" icon="el-icon-delete" size="mini" :disabled="multiple1"
          @click="handleDeleteDetails">删除</el-button>
        <el-table v-loading="loading" :data="typeRecordRefList" :row-class-name="rowClassName"
          @selection-change="handleDetailSelectionChange" ref="tb">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="序号" align="center" prop="typeId" width="50" v-if="false" />

          <el-table-column label="投资人" align="center" prop="investor" width="350">
            <template slot-scope="scope">
              <el-input placeholder="请输入姓名" v-model="typeRecordRefList[scope.row.typeId - 1].investor" clearable>
              </el-input>
            </template>
          </el-table-column>
          <el-table-column label="投资金额(元)" align="center" prop="investment" width="350">
            <template slot-scope="scope">
              <el-input placeholder="请输入金额" v-model="typeRecordRefList[scope.row.typeId - 1].investment" clearable>
              </el-input>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-dialog>

    <el-dialog :title="infoTitle" :visible.sync="showBasicInfo" width="500px" append-to-body :before-close="resetInfo">
      <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddInfo">添加</el-button>
      <el-button type="error" icon="el-icon-delete" size="mini" :disabled="multiple2"
          @click="handleDeleteDetails">删除</el-button>
      <el-table v-loading="loading" :data="typeInfoList" :row-class-name="rowClassNameInfo"
        @selection-change="handleDetailSelectionChangeInfo" ref="infoTable">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序号" align="center" prop="infoTypeId" width="50" v-if="false" />

        <el-table-column label="字段名称" align="center" prop="infoName" width="350">
          <template slot-scope="scope">
            <el-input placeholder="请输入名称" v-model="typeInfoList[scope.row.infoTypeId - 1].infoName" clearable>
            </el-input>
          </template>
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitInfoForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>
  
<script>
import { listType, getType, delType, addType, updateType, deleteSysTypeInfos,submitInfo} from "@/api/system/type";
import { listInfo, getInfo, delInfo, addInfo, updateInfo } from "@/api/system/info";

export default {
  name: "Type",
  data() {
    return {
      typeRecordRefList: [],
      checkedDetail: [],
      typeInfoList: [],
      checkedDetailInfo: [],
      showinvestment: false,
      showBasicInfo: false,
      flagInfo: "",
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      //选中的id
      typeId:null,
      //选中名称
      names: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      multiple1: true,
      multiple2: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 格信息用于保存多个格 每个格都有自己的名称 类似于Excel表格数据
      typeList: [],
      // 弹出层标题
      title: "",
      infoTitle:"添加基础信息",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        typeName: null,
        typeTime: null,
        typeInfo: null
      },
      // 表单参数
      form: {
        typeFlag: "2",
      },
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  computed: {
    isCooperating() {
      return this.form.typeFlag === "1";
    }
  },
  methods: {
    returnFlag(id) {
      if (id === '1') {
        return "合作中"
      }
      if (id === '2') {
        return "未合作"
      }
    },
    returnFlagInfo(id) {
      console.log(id)
      if (id === '1') return "primary";
      return "warning";
    },
    rowClassName({ row, rowIndex }) {
      row.typeId = rowIndex + 1;
    },
    rowClassNameInfo({ row, rowIndex }) {
      row.infoTypeId = rowIndex + 1;
    },
    //单选框选中数据
    handleDetailSelectionChange(selection) {
      if (selection.length > 1) {
        this.$refs.tb.clearSelection();
        this.$refs.tb.toggleRowSelection(selection.pop());
      } else {
        this.checkedDetail = selection;
      }
      this.multiple1 = !selection.length;
    },
    //单选框选中数据
    handleDetailSelectionChangeInfo(selection) {
      if (selection.length > 1) {
        this.$refs.infoTable.clearSelection();
        this.$refs.infoTable.toggleRowSelection(selection.pop());
      } else {
        this.checkedDetailInfo = selection;
      }
      this.multiple2 = !selection.length;
    },
    handleAddInfo() {
      if (this.typeInfoList == undefined) {
        this.typeInfoList = new Array();
      }
      let obj = {};
      obj.infoName = "";

      this.typeInfoList.push(obj);
    },
    handleAddDetails() {
      if (this.typeRecordRefList == undefined) {
        this.typeRecordRefList = new Array();
      }
      let obj = {};
      obj.investor = "";
      obj.investment = "";

      this.typeRecordRefList.push(obj);
    },
    handleDeleteDetailsInfo() {
      if (this.typeInfoList.length == 0) {
        this.$alert("请先选择要删除的数据", "提示", {
          confirmButtonText: "确定",
        });
      } else {
        this.typeInfoList.splice(this.checkedDetailInfo[0].infoTypeId - 1, 1);
      }
    },
    handleDeleteDetails() {
      if (this.typeRecordRefList.length == 0) {
        this.$alert("请先选择要删除的数据", "提示", {
          confirmButtonText: "确定",
        });
      } else {
        this.typeRecordRefList.splice(this.checkedDetail[0].typeId - 1, 1);
      }
    },

    /** 查询格信息用于保存多个格 每个格都有自己的名称 类似于Excel列表 */
    getList() {
      this.loading = true;
      listType(this.queryParams).then(response => {
        this.typeList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    resetInfo() {
      this.cancel();
    },
    // 取消按钮
    cancel() {
      this.showBasicInfo = false;
      this.open = false;
      this.typeRecordRefList = [];
      this.typeInfoList = [];
      this.checkedDetail = [];
      this.showinvestment = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        typeId: null,
        typeName: null,
        typeTime: null,
        typeInfo: null,
        typeFlag: "2",
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
      this.ids = selection.map(item => item.typeId)
      this.names = selection.map(item => item.typeName)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加表格信息";
    },
    /** 查看你按钮操作 */
    handleShow(row) {
      this.typeId = row.typeId;
      let queryForm = {};
      queryForm.infoTypeId = row.typeId;
      listInfo(queryForm).then(response => {
        this.typeInfoList = response.rows;
      })
      this.showBasicInfo = true;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const typeId = row.typeId || this.ids
      getType(typeId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改表格信息";
      });
    },
    /**提交基础信息数据 */
    submitInfoForm(){
      let queryForms = {};
      queryForms.typeId = this.typeId;
      queryForms.typeInfoList = this.typeInfoList;
      submitInfo(queryForms).then(response => {
        this.$modal.msgSuccess("提交成功");
        this.showBasicInfo = false;
        this.typeInfoList=[];
        this.getList();
      })
    },
    /** 提交按钮 */
    submitForm() {
      if (this.form.typeFlag === '2') {
        this.form.typeRecordRefList = [];
      } else {
        this.form.typeRecordRefList = this.typeRecordRefList;
      }
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.typeId != null) {
            updateType(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addType(this.form).then(response => {
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
      const typeIds = row.typeId || this.ids;
      const typeNames = row.typeName || this.names;
      this.$modal.confirm('是否确认删除表格信息为"' + typeNames + '"的数据项？').then(function () {
        return deleteSysTypeInfos(typeIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/type/export', {
        ...this.queryParams
      }, `type_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
  
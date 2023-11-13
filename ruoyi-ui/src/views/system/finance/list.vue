<template>
    <div class="app-container">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="财务收入" prop="financeIncome">
          <el-input v-model="queryParams.financeIncome" placeholder="请输入财务收入" clearable @keyup.enter.native="handleQuery" />
        </el-form-item>
        <el-form-item label="财务支出" prop="financeExpenditure">
          <el-input v-model="queryParams.financeExpenditure" placeholder="请输入财务支出" clearable
            @keyup.enter.native="handleQuery" />
        </el-form-item>
        <el-form-item label="描述" prop="financeDec">
          <el-input v-model="queryParams.financeDec" placeholder="请输入描述" clearable @keyup.enter.native="handleQuery" />
        </el-form-item>
        <el-form-item label="状态" prop="financeFlag">
          <el-select v-model="queryParams.financeFlag" placeholder="请选择状态(结清，未结)" clearable>
            <el-option v-for="dict in dict.type.sys_finanace_flag" :key="dict.value" :label="dict.label"
              :value="dict.value" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item><br>
        <!-- <div class="centered-form-item">
          <el-form-item >
            <el-radio v-model="queryParams.financeFlag" label="1">结清</el-radio>
            <el-radio v-model="queryParams.financeFlag" label="2">未结</el-radio>
          </el-form-item>
        </div> -->
      </el-form>
  
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
            v-hasPermi="['system:finance:add']">新增</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
            v-hasPermi="['system:finance:edit']">修改</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
            v-hasPermi="['system:finance:remove']">删除</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
            v-hasPermi="['system:finance:export']">导出</el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
  
      <el-table v-loading="loading" :data="financeList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <!-- <el-table-column label="财务id" align="center" prop="financeId" /> -->
        <el-table-column label="id" type="index" width="50">
        </el-table-column>
        <el-table-column label="财务收入(元)" align="center" prop="financeIncome">
          <template slot-scope="scope">
            <span>{{ scope.row.financeIncome ? scope.row.financeIncome.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',') :
              '暂无'
            }}</span>
          </template>
        </el-table-column>
  
        <el-table-column label="财务支出(元)" align="center" prop="financeExpenditure">
          <template slot-scope="scope">
            <span>{{ scope.row.financeExpenditure ? scope.row.financeExpenditure.toString().replace(/\B(?=(\d{3})+(?!\d))/g,
              ',') : '0' }}</span>
          </template>
        </el-table-column>
        <el-table-column label="所属类型" align="center" prop="sysType.typeName" />
        <el-table-column label="描述" align="center" prop="financeDec" />
        <!-- <el-table-column label="详细信息" align="center" prop="financeInfo" /> -->
        <el-table-column label="状态(结清，未结)" align="center" prop="financeFlag">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.sys_finanace_flag" :value="scope.row.financeFlag" />
          </template>
        </el-table-column>
        <el-table-column label="入账时间" align="center" prop="financeCreate" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.financeCreate, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="结账时间" align="center" prop="financeExpendTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.financeExpendTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
              v-hasPermi="['system:finance:edit']">修改</el-button>
            <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
              v-hasPermi="['system:finance:remove']">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
  
      <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
        @pagination="getList" />
  
      <!-- 添加或修改财务格主要用于存储多个格之间的信息对话框 -->
      <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
        <el-form ref="form" :model="form" :rules="rules" label-width="130px">
          <el-form-item label="财务收入(元)" prop="financeIncome">
            <el-input v-model="form.financeIncome" placeholder="请输入财务收入" />
          </el-form-item>
          <el-form-item label="财务支出(元)" prop="financeExpenditure">
            <el-input v-model="form.financeExpenditure" placeholder="请输入财务支出" />
          </el-form-item>
          <!-- <el-form-item label="所属类型" prop="financeType">
            <el-select v-model="form.financeType" filterable placeholder="请选择">
              <el-option v-for="item in financeTypes" :key="item.typeId" :label="item.typeName" :value="item.typeId">
              </el-option>
            </el-select>
          </el-form-item> -->
          <el-form-item label="入账时间" prop="financeCreate">
            <el-date-picker type="date" placeholder="选择日期" v-model="form.financeCreate" style="width: 100%;"
              value-format="yyyy-MM-dd"></el-date-picker>
          </el-form-item>
          <el-form-item label="结账时间" prop="financeExpendTime">
            <el-date-picker type="date" placeholder="选择日期" v-model="form.financeExpendTime" style="width: 100%;"
              value-format="yyyy-MM-dd"></el-date-picker>
          </el-form-item>
          <el-form-item label="描述" prop="financeDec">
            <el-input type="textarea" v-model="form.financeDec" placeholder="请输入描述" />
          </el-form-item>
          <el-form-item label="状态" prop="financeFlag">
            <el-radio-group v-model="form.financeFlag">
              <el-radio v-for="dict in dict.type.sys_finanace_flag" :key="dict.value" :label="dict.value">
                {{ dict.label }}
              </el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
        <!-- 设置自定义字段 -->
        <el-form ref="basicForm" :model="financeBasicData" label-width="120px">
          <template v-for="(item, index) in dictDateList">
            <el-form-item :label="item.infoName" :prop="item.infoName">
              <el-input v-model="financeBasicData[item.infoName]" placeholder="请输入金额"></el-input>
            </el-form-item>
          </template>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>
  
  
      <!-- 查看结账记录信息 -->
      <el-dialog :title="recordTitle" :visible.sync="recordOpen" width="80%" append-to-body>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAddRecord"
              v-hasPermi="['system:finance:add']">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multipleRecord"
              @click="handleDeleteRecord" v-hasPermi="['system:finance:remove']">删除</el-button>
          </el-col>
          <right-toolbar @queryTable="getRecordList"></right-toolbar>
        </el-row>
  
        <el-table v-loading="loading" :data="sysFinanceRecordList" @selection-change="handleRecordSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <!-- <el-table-column label="财务id" align="center" prop="financeId" /> -->
          <el-table-column label="id" type="index" width="50">
          </el-table-column>
          <el-table-column label="财务支出(元)" align="center" prop="recordMoney">
            <template slot-scope="scope">
              <span>{{ scope.row.recordMoney ?
                scope.row.recordMoney.toString().replace(/\B(?=(\d{3})+(?!\d))/g,
                  ',') : '0' }}</span>
            </template>
          </el-table-column>
          <el-table-column label="结账时间" align="center" prop="recordTime" width="180">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.recordTime, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdateRecord(scope.row)"
                v-hasPermi="['system:finance:edit']">修改</el-button>
              <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDeleteRecord(scope.row)"
                v-hasPermi="['system:finance:remove']">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
  
        <pagination v-show="recordtotal > 0" :total="recordtotal" :page.sync="queryRecordParams.pageNum"
          :limit.sync="queryRecordParams.pageSize" @pagination="getRecordList" />
  
      </el-dialog>
  
  
      <!-- 添加记录信息弹出框 -->
      <!-- 添加或修改结账记录格对话框 -->
      <el-dialog :title="recordAddTitle" :visible.sync="recordAddOpen" width="500px" append-to-body>
        <el-form ref="recordform" :model="recordform" :rules="recordrules" label-width="80px">
          <el-form-item label="结账时间" prop="recordTime">
            <el-date-picker clearable v-model="recordform.recordTime" type="date" value-format="yyyy-MM-dd"
              placeholder="请选择结账时间">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="结账金额" prop="recordMoney">
            <el-input v-model="recordform.recordMoney" placeholder="请输入结账金额" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitRecordForm">确 定</el-button>
          <el-button @click="cancelRecord">取 消</el-button>
        </div>
      </el-dialog>
  
    </div>
  </template>
  
  <script>
  import { listFinance, getFinance, delFinance, addFinance, updateFinance } from "@/api/system/finance";
  import { listRecord, getRecord, delRecord, addRecord, updateRecord } from "@/api/system/record";
  import { getFinanceBasicInfo } from "@/api/system/info";
  import { getTypeList } from "@/api/system/redis";
  
  export default {
    name: "Finance",
    dicts: ['sys_finanace_flag'],
    data() {
      return {
        // 遮罩层
        loading: true,
        recordloading: true,
        addOtherInfo: false,
        // 选中数组
        ids: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        // 非单个禁用
        singleRecord: true,
        // 非多个禁用
        multipleRecord: true,
        // 显示搜索条件
        showSearch: true,
        // 总条数
        total: 0,
        //记录总条数
        recordtotal: 0,
        // 财务格主要用于存储多个格之间的信息表格数据
        financeList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        recordOpen: false,
        recordTitle: "",
        financeTypes: [],
        sysFinanceRecordList: [],
        financeId: null,
        addShow:true,
  
        //添加记录
        recordAddOpen: false,
        recordAddTitle: "",
  
        //基础字段
        otherInfoList: [],
        financeBasicData: {},
        dictDateList:[],
  
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          financeIncome: null,
          financeExpenditure: null,
          financeType: "21",
          financeDec: null,
          financeFlag: null,
          financeCreate: null,
        },
        // 表单参数
        form: {
          financeIncome: null,
          financeExpenditure: null,
          financeType: "21",
          financeDec: null,
          financeFlag: "结清",
          financeCreate: null,
        },
        dictParams: {
          dictType: '',
          infoTypeId: null,
        },
        // 表单校验
        rules: {
          financeType: [
            { required: true, message: "表格类型不能为空", trigger: "blur" }
          ],
          financeFlag: [
            { required: true, message: "请选择状态", trigger: "blur" }
          ],
        },
        queryRecordParams: {
          pageNum: 1,
          pageSize: 10,
          financeIds: null,
          recordTime: null,
          recordMoney: null,
          operator: null
        },
        recordform: {},
        // 表单校验
        recordrules: {
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
      this.getTypeList();
    },
    watch: {
      'form.financeType': {
        handler(newValue, oldValue) {
          this.dictParams.infoTypeId = newValue;
          if (newValue != undefined) {
            this.getDictDateList();
          }
        }
      },
  
    },
    methods: {
      /** 查询财务格主要用于存储多个格之间的信息列表 */
      getList() {
        this.loading = true;
        this.queryParams.financeType = '21';
        listFinance(this.queryParams).then(response => {
          this.financeList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      getTypeList() {
        getTypeList().then(response => {
          this.financeTypes = response.rows;
        });
      },
      // 取消按钮
      cancel() {
        this.dictDateList = [];
        this.financeBasicData = {},
        this.open = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          financeId: null,
          financeIncome: null,
          financeExpenditure: null,
          financeCreate: null,
          financeUpdate: null,
          deleted: null,
          financeBalance: null,
          financeType: null,
          financeInfo: null,
          financeDec: null,
          financeFlag: '0'
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
        this.ids = selection.map(item => item.financeId)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.addShow = false;
        this.getTypeList();
        this.reset();
        this.open = true;
        this.title = "添加信息";
      },
      //查看记录按钮
      handleShowRecord(row) {
        this.queryRecordParams.financeIds = row.financeId;
        this.getRecordList();
        this.sysFinanceRecordList;
        this.financeId = row.financeId;
        this.recordOpen = true;
        this.recordTitle = "查看结账记录信息";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.addShow = true;
        this.reset();
        const financeId = row.financeId || this.ids
        getFinance(financeId).then(response => {
          this.form = response.data;
          this.riskHiddenData = JSON.parse(response.data.financeInfo);
          // this.form.financeType = response.data.sysType.typeName;
          this.open = true;
          this.title = "修改信息";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            let strCustomFields = JSON.stringify(this.financeBasicData);
            this.form.financeInfo = strCustomFields;
            this.form.financeType = '21';
            if (this.form.financeId != null) {
              console.log(this.form)
              updateFinance(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addFinance(this.form).then(response => {
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
        const financeIds = row.financeId || this.ids;
        this.$modal.confirm('是否确认删除财务格主要用于存储多个格之间的信息编号为"' + financeIds + '"的数据项？').then(function () {
          return delFinance(financeIds);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => { });
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('system/finance/export', {
          ...this.queryParams
        }, `finance_${new Date().getTime()}.xlsx`)
      },
  
      //基础数据
      /** 查询该类型的隐蔽致灾因素的具体字段*/
      getDictDateList() {
        this.dictDateList = []
        getFinanceBasicInfo(this.dictParams).then(response => {
          this.dictDateList = response.rows;
        });
      },
  
  
      // record方法
  
      getRecordList() {
        this.recordloading = true;
        listRecord(this.queryRecordParams).then(response => {
          this.sysFinanceRecordList = response.rows;
          this.recordtotal = response.total;
          this.recordloading = false;
        });
      },
      handleAddRecord(row) {
        this.recordAddOpen = true;
        this.recordAddTitle = "新增结账记录信息";
      },
      // 取消按钮
      cancelRecord() {
        this.recordAddOpen = false;
        this.resetRecord();
      },
      // 表单重置
      resetRecord() {
        this.recordform = {
          id: null,
          recordTime: null,
          financeIds: null,
          recordMoney: null,
          operator: null
        };
        this.resetForm("recordform");
        this.recordform.financeIds = this.financeId;
      },
      // 多选框选中记录数据
      handleRecordSelectionChange(selection) {
        this.ids = selection.map(item => item.id)
        this.singleRecord = selection.length !== 1
        this.multipleRecord = !selection.length
      },
  
      /** 修改按钮操作 */
      handleUpdateRecord(row) {
        this.resetRecord();
        const id = row.id || this.ids
        getRecord(id).then(response => {
          this.recordform = response.data;
          this.recordAddOpen = true;
          this.title = "修改结账记录格";
        });
      },
      /** 提交按钮 */
      submitRecordForm() {
        this.$refs["recordform"].validate(valid => {
          if (valid) {
            this.recordform.financeIds = this.financeId;
            if (this.recordform.id != null) {
              updateRecord(this.recordform).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.recordAddOpen = false;
                this.getRecordList();
                this.getList();
                this.resetRecord();
              });
            } else {
              addRecord(this.recordform).then(response => {
                this.$modal.msgSuccess("新增成功");
                this.recordAddOpen = false;
                this.getRecordList();
                this.getList();
                this.resetRecord();
              });
            }
          }
        });
      },
      /** 删除按钮操作 */
      handleDeleteRecord(row) {
        const ids = row.id || this.ids;
        let queryForm = {};
        queryForm.ids = ids;
        queryForm.financeId = this.financeId;
        this.$modal.confirm('是否确认删除结账记录格编号为"' + ids + '"的数据项？').then(function () {
          return delRecord(queryForm);
        }).then(() => {
          this.getRecordList();
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => { });
      },
    }
  };
  </script>
  <style>
  .centered-form-item {
    width: 100%;
    display: flex;
    justify-content: center;
  }
  </style>
  
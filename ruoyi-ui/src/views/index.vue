<template>
  <div class="dashboard-editor-container">
    <panel-group @handleSetLineChartData="handleSetLineChartData" />
    <!-- <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
      <line-chart :chart-data="lineChartData" />
    </el-row> -->
    <el-row v-show="showInfo" style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
      <el-col :xs="24" :sm="24" :lg="16">
        <div v-show="show == true" class="echarts-info" id="myChartChange" style="width: 100%; height: 500px;"></div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <div v-show="show == true" class="echarts-info" id="pieChartChange"
          style="width: 100%; height: 500px; padding-top: 30px;"></div>
      </el-col>
    </el-row>
    <el-row v-show="!showInfo" style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
      <div class="block">
        <el-date-picker @change="handleDateChange" v-model="value2" type="daterange" align="right" unlink-panels
          range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" :picker-options="pickerOptions"
          format="yyyy-MM-dd">
        </el-date-picker>
      </div>
      <el-col :xs="24" :sm="24" :lg="16">
        <div v-show="show" class="echarts-info" id="lineChartChange" style="width: 100%; height: 500px;"></div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <div v-show="show" class="echarts-info" id="pieChartInfo"
          style="width: 100%; height: 500px; padding-top: 30px; margin-right: 100px;"></div>
      </el-col>
    </el-row>
    <el-row>
      <el-col :xs="24" :sm="24" :lg="24">
        <div v-show="show == false">
          <el-empty description="暂无数据"></el-empty>
        </div>
      </el-col>
    </el-row>
    <!-- <el-row :gutter="32">
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <raddar-chart />
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <pie-chart />
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <bar-chart />
        </div>
      </el-col>
    </el-row> -->
  </div>
</template>

<script>
import PanelGroup from './dashboard/PanelGroup'
import StaticChart from './dashboard/StaticChart'
import RaddarChart from './dashboard/RaddarChart'
import PieChart from './dashboard/PieChart'
import BarChart from './dashboard/BarChart'
import { getStaticInfo, getStaticInfoList, getLineInfo, getLineInfoList } from "@/api/system/static";


const info = {
  "expend": {
    name: "支出"
  },
  "income": {
    name: "收入"
  },
  "shangdong": {
    name: "山东"
  },
  "xinjiang": {
    name: "新疆"
  }
}

export default {
  name: "Index",
  components: {
    PanelGroup,
    RaddarChart,
    PieChart,
    BarChart,
    StaticChart
  },
  data() {
    return {
      data: [],
      show: true,
      showInfo: true,
      array: [],
      chart: null,
      piechart: null,
      linechart: null,
      pieChartInfo: null,
      title: "支出",
      type: "",
      barChartData: {

      },
      lineChartData: {

      },
      query: {
        financeCreate: "2022-01-01",
        financeExpendTime: "",
      },
      info: {},
      pickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近一个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近三个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
            picker.$emit('pick', [start, end]);
          }
        }]
      },
      value1: '',
      value2: ''
    }
  },

  mounted() {
    this.chart = this.$echarts.init(document.getElementById('myChartChange'), 'macarons');
    this.piechart = this.$echarts.init(document.getElementById('pieChartChange'), 'macarons');
    this.linechart = this.$echarts.init(document.getElementById('lineChartChange'), 'macarons');
    this.pieChartInfo = this.$echarts.init(document.getElementById('pieChartInfo'), 'macarons');
    this.getStaticList("expend");
    this.getStaticInfo();
    this.init();

  },
  created() {

  },
  watch: {
    showInfo(v) {
      if (!v) {
        this.$nextTick(() => {
          this.linechart.resize();
          this.pieChartInfo.resize();
        });
      }
    }
  },
  methods: {
    init() {
      const currentDate = new Date();
      this.query.financeExpendTime = currentDate.toISOString().substring(0, 10); // 格式化为 YYYY-MM-DD 格式的日期字符串
    },
    getStaticInfo() {
      getStaticInfo().then(respone => {
        info.expendAll = respone.expendAll;
        info.incomeAll = respone.incomeAll;
        info.incomeAllInfo = respone.incomeAllInfo;
        info.expendAllInfo = respone.expendAllInfo;
      })
    },
    setOptions({ arrayName, arrayMoney } = {}) {
      this.chart.setOption({
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            data: arrayName,
            axisTick: {
              alignWithLabel: true
            }
          }
        ],
        yAxis: [
          {
            type: 'value'
          }
        ],
        series: [
          {
            name: '金额',
            type: 'bar',
            barWidth: '60%',
            data: arrayMoney
          }
        ]
      })
    },
    setPieOptions({ arrayName, arrayMoney, pieInfo } = {}) {
      this.piechart.setOption({
        title: {
          text: this.title,
          subtext: '数据详情',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        series: [
          {
            name: '金额',
            type: 'pie',
            radius: '50%',
            data: pieInfo,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      })
    },
    setLineOptions(dateInfo, expendInfo, incomeInfo) {
      this.linechart.setOption({
        tooltip: {
          trigger: 'axis'
        },
        xAxis: {
          type: 'category',
          data: dateInfo
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: "出款",
            data: expendInfo,
            type: 'line'
          },
          {
            name: "回款",
            data: incomeInfo,
            type: 'line'
          }
        ]
      })
    },
    setPieChartOptions(pieInfo) {
      this.pieChartInfo.setOption({
        title: {
          text: this.title,
          subtext: '数据详情',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        series: [
          {
            name: '金额',
            type: 'pie',
            radius: '50%',
            data: pieInfo,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      })
    },

    getStaticList(type) {
      if (type == 'expend' || type == 'income') {
        this.showInfo = true;
        getStaticInfoList(type).then(response => {
          this.barChartData.arrayName = response.arrayName;
          this.barChartData.arrayMoney = response.arrayMoney;
          this.barChartData.pieInfo = response.pieInfo;
          if (response.arrayName.length == 0) {
            this.show = false;
            return;
          }
          this.show = true;
          this.setOptions(this.barChartData);
          this.setPieOptions(this.barChartData);
        })
      }
      if (type == 'shangdong' || type == 'shangdong') {
        this.showInfo = false;
        getLineInfoList(this.query).then(response => {
          this.lineChartData.dateInfo = response.dateInfo;
          this.lineChartData.expendInfo = response.expendInfo;
          this.lineChartData.incomeInfo = response.incomeInfo;
          if (response.dateInfo.length == 0) {
            this.show = false;
            return;
          }
          this.show = true;
          const dataList = this.lineChartData.dateInfo;
          this.setLineOptions(dataList, this.lineChartData.expendInfo, this.lineChartData.incomeInfo);
          this.setPieChartOptions(response.pieInfo);
        })
      }
    },
    handleDateChange(date) {
      // 处理日期变化的逻辑
      if (date == null) {
        this.query.financeCreate = "2022-01-01";
        let currentDate = new Date();
        this.query.financeExpendTime = currentDate.toISOString().substring(0, 10);
      } else {
        this.query.financeCreate = date[0].toISOString().substring(0, 10);
        this.query.financeExpendTime = date[1].toISOString().substring(0, 10);
      }
      console.log(this.query)
      this.getStaticList(this.type);

    },
    handleSetLineChartData(type) {
      this.type = type;
      this.title = info[type].name;
      this.getStaticList(type)
    },

  }
}
</script>


<style lang="scss" scoped>
.block {
  margin-left: 300px;
}

.dashboard-editor-container {
  padding: 32px;
  background-color: rgb(240, 242, 245);
  position: relative;

  .github-corner {
    position: absolute;
    top: 0px;
    border: 0;
    right: 0;
  }

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}

@media (max-width:1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
</style>

<!-- 
<style scoped>
.app-container {
  background-color: #f0f2f5;
}

#myChart {
  color: white;
}

.echarts-info {
  height: 500px;
  background-color: white;
  margin-bottom: 20px;
}
</style> -->


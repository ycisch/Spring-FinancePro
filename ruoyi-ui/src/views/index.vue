<template>
  <div class="app-container home">
    <el-row :gutter="20">
      <el-col :span="24">
        <div class="echarts-info" id="myChartChange"></div>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="8">
        <div class="echarts-info" id="myChart"></div>
      </el-col>
      <el-col :span="8">
        <div class="echarts-info" id="myChart1"></div>
      </el-col>
      <el-col :span="8">
        <div class="echarts-info" id="myChart2"></div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "Index",
  data() {
    return {
      data: [],
      title: [],
      array: []
    }
  },

  mounted() {
    this.init();
  },
  created() {

  },
  methods: {

    init() {
      this.myChartChange();
      this.drawLine();
    },
    myChartChange() {
      let myChartChange = this.$echarts.init(document.getElementById('myChartChange'))
      let option = {
        title: {
          text: 'Stacked Line'
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['Email', 'Union Ads', 'Video Ads', 'Direct', 'Search Engine']
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        toolbox: {
          feature: {
            saveAsImage: {}
          }
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: 'Email',
            type: 'line',
            stack: 'Total',
            data: [120, 132, 101, 134, 90, 230, 210]
          },
          {
            name: 'Union Ads',
            type: 'line',
            stack: 'Total',
            data: [220, 182, 191, 234, 290, 330, 310]
          },
          {
            name: 'Video Ads',
            type: 'line',
            stack: 'Total',
            data: [150, 232, 201, 154, 190, 330, 410]
          },
          {
            name: 'Direct',
            type: 'line',
            stack: 'Total',
            data: [320, 332, 301, 334, 390, 330, 320]
          },
          {
            name: 'Search Engine',
            type: 'line',
            stack: 'Total',
            data: [820, 932, 901, 934, 1290, 1330, 1320]
          }
        ]
      };
      myChartChange.setOption(option,true);
    },
    drawLine() {
      // 基于准备好的dom，初始化echarts实例
      let myChart = this.$echarts.init(document.getElementById('myChart'))
      let myChart1 = this.$echarts.init(document.getElementById('myChart1'))
      let myChart2 = this.$echarts.init(document.getElementById('myChart2'))
      // 绘制图表
      let option = {
        show: true,
        title: {
          text: '每日支出与收入',
          left: 'center',
          padding: [
            20, 0, 0, 0
          ]
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          top: 'bottom'
        },
        series: [
          {
            name: '详细信息',
            type: 'pie',
            radius: '50%',
            data: [
              { value: 1048, name: '收入' },
              { value: 735, name: '支出' },
            ],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]

      };
      myChart.setOption(option, true);
      myChart1.setOption(option, true);
      myChart2.setOption(option, true);
    },
  }
}
</script>



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
</style>


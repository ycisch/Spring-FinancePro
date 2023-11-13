<template>
    <div :class="className" :style="{ height: height, width: width }" />
</template>
  
<script>
import * as echarts from 'echarts';
require('echarts/theme/macarons') // echarts theme
import resize from './mixins/resize'

export default {
    mixins: [resize],
    props: {
        className: {
            type: String,
            default: 'chart'
        },
        width: {
            type: String,
            default: '100%'
        },
        height: {
            type: String,
            default: '350px'
        },
        autoResize: {
            type: Boolean,
            default: true
        },
        chartData: {
            type: Object,
            required: true
        }
    },
    data() {
        return {
            chart: null
        }
    },
    watch: {
        chartData: {
            deep: true,
            handler(val) {
                this.setOptions(val)
            }
        }
    },
    mounted() {
        this.$nextTick(() => {
            this.initChart()
        })
    },
    beforeDestroy() {
        if (!this.chart) {
            return
        }
        this.chart.dispose()
        this.chart = null
    },
    methods: {
        initChart() {
            this.chart = echarts.init(this.$el, 'macarons')
            this.setOptions(this.chartData)
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
        }
    }
}
</script>
  
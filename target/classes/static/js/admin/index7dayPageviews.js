var main =$("#main");
main.height(main.width()*0.4);
// 基于准备好的dom，初始化echarts实例
var myChart = echarts.init(document.getElementById("main"));
//"2017-12-1","2017-12-2","2017-12-3","2017-12-4","2017-12-5","2017-12-6","2017-12-7"
// 指定图表的配置项和数据
// 指定图表的配置项和数据
var option = {
    title: {
        text: '主页七日浏览量'
    },
    tooltip: {},
    legend: {
        data:['浏览量']
    },
    xAxis: {
        data: []
    },
    yAxis: {},
    series: [{

        name: '浏览量',
        type: 'line',
        data: []
    }]
};
// 异步加载数据
$.get("/qgyblog/user/blog/get/7day/pageviews").done(function (data) {
    // 填入数据
    myChart.setOption({
        xAxis: {
            data: data.categories
        },
        series: [{
            // 根据名字对应到相应的系列
            name: '浏览量',
            data: data.data
        }]
    });
});
// 使用刚指定的配置项和数据显示图表。
myChart.setOption(option);
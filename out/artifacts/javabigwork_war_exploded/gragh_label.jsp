
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>title</title>

    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="font-awesome/css/font-awesome.css" rel="stylesheet">

    <link href="css/animate.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">

</head>

<style>
    body {
        font-family:"Lucida Grande","Droid Sans",Arial,Helvetica,sans-serif;
    }

</style>

<body>

<div id="wrapper">

    <nav class="navbar-default navbar-static-side" role="navigation">
        <div class="sidebar-collapse">
            <ul class="nav metismenu" id="side-menu">
                <li class="nav-header">
                    <div class="dropdown profile-element">
                        <span>
                            <img src="img/龙猫.png" class="img-circle" alt="img">
                        </span>
                        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                            <span class="clear"> <span class="block m-t-xs"> <strong class="font-bold">青铜小姐姐</strong>
                             </span> <span class="text-muted text-xs block">管理员 <b class="caret"></b></span> </span> </a>
                        <ul class="dropdown-menu animated fadeInRight m-t-xs">
                            <li><a href="index.jsp">登出</a></li>
                        </ul>
                    </div>
                    <div class="logo-element">
                        IN+
                    </div>
                </li>
                <li>
                    <a href="homepage.jsp"><i class="fa fa-th-large"></i> <span class="nav-label">主页</span></a>
                </li>
                <li>
                    <a href="datatable.jsp"><i class="fa fa-th-large"></i> <span class="nav-label">表格</span></a>
                </li>
                <li>
                    <a href="graph_flot.jsp"><i class="fa fa-bar-chart-o"></i> <span class="nav-label">flot图表</span> </a>
                </li>
                <li>
                    <a href="answer.jsp"><i class="fa fa-diamond"></i> <span class="nav-label">简单问答</span> </a>
                </li>
                <li>
                    <a href="d3.jsp"><i class="fa fa-diamond"></i> <span class="nav-label">d3</span> </a>
                </li>
                <li class="active">
                    <a href="gragh_label.jsp"><i class="fa fa-diamond"></i> <span class="nav-label">graghlabel</span> </a>
                </li>
            </ul>

        </div>
    </nav>

    <div id="page-wrapper" class="gray-bg">
        <div class="row border-bottom">
            <nav class="navbar navbar-static-top white-bg" role="navigation" style="margin-bottom: 0">
                <div class="navbar-header">
                    <a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i> </a>
                </div>
                <ul class="nav navbar-top-links navbar-right">
                    <li>
                        <a href="index.jsp">
                            <i class="fa fa-sign-out"></i> 退出
                        </a>
                    </li>
                </ul>

            </nav>
        </div>
        <div class="wrapper wrapper-content animated fadeInRight">
            <div class="row">
                <div>毒品标签云图</div>
                <div class="col-lg-6" id="tagCloud">
                    <%--代码一--%>

                </div>
                <div>毒品城市对应图</div>
                <div class="col-lg-6" id="verticalBP">
                    <%--代码二--%>

                </div>
            </div>
        </div>
    </div>
</div>
<!-- Mainly scripts -->
<script src="js/jquery-3.1.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="js/plugins/slimscroll/jquery.slimscroll.min.js"></script>

<!-- Custom and plugin javascript -->
<script src="js/inspinia.js"></script>
<script src="js/plugins/pace/pace.min.js"></script>

<script src="http://d3js.org/d3.v4.min.js"></script>
<script src="js/d3/d3.layout.cloud.js"></script>
<script src="js/d3/d3-viz.js"></script>


<script>

    d3.json("data/tagCloud.json", function(error, tagCloud) {
        if (error) throw error;

        let data = tagCloud.example;

        let words = data.map(item => { // 处理原始数据
            return {
                text: item.text,
                size: 10 + item.size * 8,
                href: item.href,
            };
    });

        let color = d3.scaleOrdinal(d3.schemeCategory20);

        let layout = d3.layout.cloud()
            .size([800, 800])
            .words(words)
            .padding(5)
            .rotate(function() { return ~~(Math.random() * 2) * 90; })
            .font("Impact")
            .fontSize(function(d) { return Math.sqrt(d.size); })
            .on("end", draw);

        layout.start();


        function draw(words) {
            let g = d3
                .select("#tagCloud").append("svg")
                .attr("width", 800)
                .attr("height", 800)
                .append("g")
                .attr("transform", "translate(400,400)");

            g.selectAll("text")
                .data(words)
                .enter().append("text")
                .on('click',function(d){
                    window.open(d.href);
                })
                .style("font-family", "Impact")
                .style("cursor", "pointer")
                .style("fill", function(d, i) { return color(i); })
                .attr("text-anchor", "middle")
                .attr("transform", function(d) {
                    return "translate(" + [d.x, d.y] + ")rotate(" + d.rotate + ")";
                })
                .style("font-size", function(d) { return d.size * 1.2 + "px"; })
                .text(function(d) { return d.text; })
                .append('title')
                .text(function(d) { return d.href; });

            g.selectAll("text") // 创建动画
                .style('fill-opacity', 0)
                .transition()
                .duration(200)
                .delay(function(d,i){
                    return i * 200;
                })
                .style('fill-opacity', 1)

        }
    });
</script>

<script>
    const containerWidth = 800;
    const margin = { top: 80, right: 80, bottom: 30, left: 60 };
    const width = containerWidth - margin.left - margin.right;
    const height = 800 - margin.top - margin.bottom;
    let chart = d3.select("#verticalBP").append("svg")
        .attr("width", width + margin.left + margin.right)
        .attr("height", height + margin.top + margin.bottom);

    let z = d3.scaleOrdinal()
        .range(d3.schemeCategory20);

    const data = [
        ['天猫', '上海', 15216, 25216],
        ['线下自营店', '上海', 11278, 13244],
        ['苏宁易购', '上海', 27, 24],
        ['网上自营店', '上海', 27648, 35411],
        ['线下代理商', '上海', 1551, 1545],
        ['京东', '上海', 22141, 25441],
        ['天猫', '广州', 15453, 15353],
        ['线下自营店', '广州', 24683, 24623],
        ['苏宁易购', '广州', 1862, 654],
        ['线下代理商', '广州', 16228, 13228],
        ['天猫', '北京', 15001, 18001],
        ['线下自营店', '北京', 15001, 1654],
        ['苏宁易购', '北京', 5001, 6541],
        ['网上自营店', '北京', 28648, 29648],
        ['线下代理商', '北京', 9648, 9648],
        ['天猫', '深圳', 3313, 541],
        ['线下自营店', '深圳', 22396, 24396],
        ['苏宁易购', '深圳', 3362, 3762],
        ['网上自营店', '深圳', 22396, 21396],
        ['线下代理商', '深圳', 2473, 2973],
        ['京东', '深圳', 16541, 11541],
        ['苏宁易购', '杭州', 3541, 3599],
        ['线下代理商', '杭州', 3541, 8741],
        ['京东', '杭州', 3654, 9874],
        ['天猫', '武汉', 1738, 110],
        ['线下自营店', '武汉', 12925, 13],
        ['苏宁易购', '武汉', 15413, 0],
        ['线下自营店', '重庆', 2166, 654],
        ['苏宁易购', '重庆', 2286, 3654],
        ['网上自营店', '重庆', 348, 3654],
        ['线下代理商', '重庆', 4244, 3654],
        ['京东', '重庆', 1536, 1654],
        ['线下自营店', '长沙', 351, 654],
        ['线下代理商', '长沙', 405, 541],
        ['线下自营店', '成都', 914, 654],
        ['苏宁易购', '成都', 127, 354],
        ['线下代理商', '成都', 1470, 654],
        ['京东', '成都', 516, 354],
        ['天猫', '东莞', 43, 0],
        ['线下自营店', '东莞', 667, 654],
        ['苏宁易购', '东莞', 172, 354],
        ['网上自营店', '东莞', 149, 541],
        ['线下代理商', '东莞', 1380, 3254],
        ['京东', '东莞', 791, 754],
        ['线下自营店', '苏州', 541, 687],
        ['线下代理商', '苏州', 654, 541],
        ['线下自营店', '南京', 1070, 654],
        ['线下代理商', '南京', 1171, 1541],
        ['京东', '南京', 33, 45],
        ['线下自营店', '佛山', 407, 654],
        ['苏宁易购', '佛山', 541, 874],
        ['线下代理商', '佛山', 457, 674],
        ['京东', '佛山', 541, 365],
        ['线下自营店', '天津', 557, 654],
        ['苏宁易购', '天津', 167, 541],
        ['网上自营店', '天津', 95, 100],
        ['线下代理商', '天津', 1090, 1321],
        ['京东', '天津', 676, 541],
        ['天猫', '合肥', 1195, 1654],
        ['线下自营店', '合肥', 5412, 6541],
        ['苏宁易购', '合肥', 212, 241],
        ['线下代理商', '合肥', 1509, 1654],
        ['天猫', '温州', 3899, 389],
        ['线下自营店', '温州', 147, 321],
        ['苏宁易购', '温州', 455, 541],
        ['网上自营店', '温州', 321, 254],
        ['线下代理商', '温州', 4100, 4512],
        ['天猫', '南宁', 123, 133],
        ['线下自营店', '南宁', 634, 654],
        ['苏宁易购', '南宁', 749, 541],
        ['网上自营店', '南宁', 119, 654],
        ['线下代理商', '南宁', 3705, 4574],
        ['京东', '南宁', 3456, 4000],
        ['线下自营店', '厦门', 828, 1201],
        ['苏宁易购', '厦门', 2808, 3541],
        ['网上自营店', '厦门', 1452, 2000],
        ['线下代理商', '厦门', 2625, 1541],
        ['京东', '厦门', 1920, 1234],
        ['线下自营店', '西安', 1146, 1541],
        ['苏宁易购', '西安', 212, 321],
        ['网上自营店', '西安', 223, 241],
        ['线下代理商', '西安', 1803, 2000],
        ['京东', '西安', 761, 465],
        ['线下自营店', '长春', 527, 654],
        ['苏宁易购', '长春', 90, 120],
        ['线下代理商', '长春', 930, 1241],
        ['京东', '长春', 395, 410],
        ['天猫', '哈尔滨', 7232, 8451],
        ['线下自营店', '哈尔滨', 1272, 2141],
        ['苏宁易购', '哈尔滨', 1896, 3541],
        ['网上自营店', '哈尔滨', 200, 1241],
        ['线下代理商', '哈尔滨', 10782, 15412],
        ['京东', '哈尔滨', 1911, 2000],
        ['线下自营店', '青岛', 495, 521],
        ['苏宁易购', '青岛', 432, 541],
        ['网上自营店', '青岛', 241, 320],
        ['线下代理商', '青岛', 1557, 1600],
        ['京东', '青岛', 24, 30],
        ['线下自营店', '沈阳', 460, 541],
        ['网上自营店', '沈阳', 88, 99],
        ['线下代理商', '沈阳', 956, 365],
        ['线下自营店', '济南', 232, 365],
        ['苏宁易购', '济南', 71, 99],
        ['线下代理商', '济南', 575, 654],
        ['京东', '济南', 368, 354]
    ];

    let g =[chart.append("g").attr("transform","translate(300,100)")];

    chart.append("text").attr("x", 400).attr("y",50)
        .attr("class","vbp-header").attr('font-size', '14px').attr('font-weight', '700').text("目标出货量");


    let bp=[ viz.bP() // 定义两个BP图
        .data(data)
        .min(12)
        .pad(1)
        .height(700)
        .width(300)
        .barSize(35)
        .fill(d=>z(d.primary))
    // ,viz.bP()
    //     .data(data)
    //     .value(d=>d[3])
    //     .min(12)
    //     .pad(1)
    //     .height(700)
    //     .width(300)
    //     .barSize(35)
    //     .fill(d=>z(d.primary))
    ];

    [0].forEach(function(i){// 输出两个BP图数据
        g[i].call(bp[i]); // 输出两个BP图

        g[i].append("text").attr("x",-50).attr("y",-8).style("text-anchor","middle").text("出货渠道");
        g[i].append("text").attr("x", 350).attr("y",-8).style("text-anchor","middle").text("城市");

        g[i].selectAll(".mainBars")
            .on("mouseover",mouseover)
            .on("mouseout",mouseout);

        g[i].selectAll(".mainBars").append("text").attr("class","label")
            .attr("x",d=>(d.part==="primary"? -30: 30))
    .attr("y",d=>+6)
    .text(d=>d.key)
    .attr("text-anchor",d=>(d.part==="primary"? "end": "start"));

        g[i].selectAll(".mainBars").append("text").attr("class","perc")
            .attr("x",d=>(d.part==="primary"? -100: 80))
    .attr("y",d=>+6)
    .text(function(d){
            return d3.format("0.0%")(d.percent);
        })
            .attr("text-anchor",d=>(d.part==="primary"? "end": "start"));

        g[i].selectAll(".mainBars") // hover
            .append('title')
            .text(function(d){
                return d.key + '\n' + d.value + ' 台手机';
            });

    });

    chart.append('g')// 输出标题
        .attr('class', 'vertical-bp-chart--title')
        .append('text')
        .attr('fill', '#000')
        .attr('font-size', '16px')
        .attr('font-weight', '700')
        .attr('text-anchor', 'middle')
        .attr('x', containerWidth / 2)
        .attr('y', 20)
        .text('XX手机2016年全国各渠道出货量数据汇总');

    function mouseover(d){
        [0].forEach(function(i){
            bp[i].mouseover(d);

            g[i].selectAll(".mainBars").select(".perc")
                .text(function(d){ return d3.format("0.0%")(d.percent)});

            g[i].selectAll(".mainBars")
                .select('title')
                .text(function(d){
                    return d.key + '\n' + d.value + ' 台手机';
                });
        });
    }
    function mouseout(d){
        [0].forEach(function(i){
            bp[i].mouseout(d);

            g[i].selectAll(".mainBars").select(".perc")
                .text(function(d){ return d3.format("0.0%")(d.percent)});

            g[i].selectAll(".mainBars")
                .select('title')
                .text(function(d){
                    return d.key + '\n' + d.value + ' 台手机';
                });
        });
    }
</script>

</body>

</html>

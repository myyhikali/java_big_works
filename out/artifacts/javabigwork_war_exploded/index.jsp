<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="tools.Csv"%>
<%@ page import="javafx.util.Pair"%>


<!DOCTYPE html>
<html>
<head>



    <title>力导向图</title>

    <script type="text/javascript" src="http://d3js.org/d3.v5.min.js"></script>

    <!--<link rel="stylesheet" type="text/css" href="./styles.css">-->

</head>
<body>
<script>
    var w = window,
        d = document,
        e = d.getElementsByTagName('body')[0];  // 获得body标签得数据

    var width = d.clientWidth ||e.clientWidth  || e.clientWidth;      // 可以根据标签修改
    var height = w.innerHeight || d.clientHeight || e.clientHeight  ; // 可以根据标签修改

    var svg =d3.select("body").append("svg")
        .attr("width",width)
        .attr("height",height);


    var g = svg
        .append("g")
        .call(d3.zoom()
            .scaleExtent([1, 10])
            .on("zoom", zoomed));



    //准备数据
    <%
        Pair <String[], ArrayList<String[]>> data = Csv.read("C:\\source\\javabigwork\\tmp\\csv\\舟山.csv", true, "UTF-8");
        String[] header = data.getKey();
//        System.out.println(header);
        ArrayList<String[]> dataSet = data.getValue();
//        System.out.println(dataSet);
    %>
    var nodes = [];
    <%
        for(String[] a: dataSet){
            for(String b: a){
                %>
                nodes.push({name: "<%= b %>"});
                <%
            }
        }
    %>

    // var nodes = [
    //     {name:"湖南邵阳"},
    //     {name:"山东莱州"},
    //     {name:"广东阳江"},
    //     {name:"山东枣庄"},
    //     {name:"泽"},
    //     {name:"恒"},
    //     {name:"鑫"},
    //     {name:"明山"},
    //     {name:"班长"}
    // ];

    // var edges = [
    //     {source:0,target:4,relation:"籍贯",value:1.3},
    //     {source:4,target:5,relation:"舍友",value:1},
    //     {source:4,target:6,relation:"舍友",value:1},
    //     {source:4,target:7,relation:"舍友",value:1},
    //     {source:1,target:6,relation:"籍贯",value:2},
    //     {source:2,target:5,relation:"籍贯",value:0.9},
    //     {source:3,target:7,relation:"籍贯",value:1},
    //     {source:5,target:6,relation:"同学",value:1.6},
    //     {source:6,target:7,relation:"朋友",value:0.7},
    //     {source:6,target:8,relation:"职责",value:2}
    // ];
    var edges = [];

    <%
        for(int i = 0 ; i < dataSet.size(); ++i){
            for (int j = 0; j < dataSet.get(i).length; ++j){
                %>
                    edges.push({source:<%= i + i * (dataSet.get(i).length-1) %>,target:<%= j + i * (dataSet.get(i).length-1)%> ,relation: "<%= header[j]%>",value:10});
                <%
            }
        }
    %>


    //设置一个color的颜色比例尺，为了让不同的扇形呈现不同的颜色
    var colorScale = d3.scaleOrdinal()
        .domain(d3.range(nodes.length))
        .range(d3.schemeCategory10);

    //新建一个力导向图
    var forceSimulation = d3.forceSimulation()
        .force("link",d3.forceLink())
        .force("charge",d3.forceManyBody())
        .force("center",d3.forceCenter());

    //初始化力导向图，也就是传入数据
    //生成节点数据
    forceSimulation.nodes(nodes)
        .on("tick",ticked);//这个函数很重要，后面给出具体实现和说明
    //生成边数据
    forceSimulation.force("link")
        .links(edges)
        .distance(function(d){//每一边的长度
            return d.value*100;
        });
    //设置图形的中心位置
    forceSimulation.force("center")
        .x(width/2)
        .y(height/2);
    //在浏览器的控制台输出
    console.log(nodes);
    console.log(edges);

    //有了节点和边的数据后，我们开始绘制
    //绘制边
    var links = g.append("g")
        .selectAll("line")
        .data(edges)
        .enter()
        .append("line")
        .attr("stroke",function(d,i){
            return colorScale(i);
        })
        .attr("stroke-width",1);
    var linksText = g.append("g")
        .selectAll("text")
        .data(edges)
        .enter()
        .append("text")
        .text(function(d){
            return d.relation;
        });

    //绘制节点
    //老规矩，先为节点和节点上的文字分组
    var gs = g.selectAll(".circleText")
        .data(nodes)
        .enter()
        .append("g")
        .attr("transform",function(d,i){
            var cirX = d.x;
            var cirY = d.y;
            return "translate("+cirX+","+cirY+")";
        })
        .call(d3.drag()
            .on("start",started)
            .on("drag",dragged)
            .on("end",ended)
        );

    //绘制节点
    gs.append("circle")
        .attr("r",10)
        .attr("fill",function(d,i){
            return colorScale(i);
        });
    //文字
    gs.append("text")
        .attr("x",-10)
        .attr("y",-20)
        .attr("dy",10)
        .text(function(d){
            return d.name;
        });

    function ticked(){
        links
            .attr("x1",function(d){return d.source.x;})
            .attr("y1",function(d){return d.source.y;})
            .attr("x2",function(d){return d.target.x;})
            .attr("y2",function(d){return d.target.y;});

        linksText
            .attr("x",function(d){
                return (d.source.x+d.target.x)/2;
            })
            .attr("y",function(d){
                return (d.source.y+d.target.y)/2;
            });

        gs
            .attr("transform",function(d) { return "translate(" + d.x + "," + d.y + ")"; });
    }
    function started(d){
        if(!d3.event.active){
            forceSimulation.alphaTarget(0.8).restart();
        }
        d.fx = d.x;
        d.fy = d.y;
    }
    function dragged(d){
        d.fx = d3.event.x;
        d.fy = d3.event.y;
    }
    function ended(d){
        if(!d3.event.active){
            forceSimulation.alphaTarget(0);
        }
        d.fx = null;
        d.fy = null;
    }

    function updateWindow(){    // 根据窗口缩放
        var width = e.clientWidth || w.innerWidth || d.clientWidth ;      // 可以根据标签修改
        var height = d.clientHeight || e.clientHeight || w.innerHeight;  // 可以根据标签修改

        svg.attr("width", width).attr("height", height);

        forceSimulation.force("center")
            .x(width/2)
            .y(height/2);
    }
    //监听

    function zoomed(){
        g.attr( "transform",d3.event.transform );
    }

    d3.select(window).on('resize', updateWindow);

</script>
</body>
</html>
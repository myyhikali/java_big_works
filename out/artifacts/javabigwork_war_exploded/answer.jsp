<%@ page import="python.Console" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.fasterxml.jackson.databind.ObjectMapper.*" %>
<%@ page import="com.fasterxml.jackson.core.JsonProcessingException.*" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>简单问答</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="css/animate.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">

</head>

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
                             </span> <span class="text-muted text-xs block">管理员<b class="caret"></b></span> </span> </a>
                        <ul class="dropdown-menu animated fadeInRight m-t-xs">
                            <li><a href="#">注销</a></li>
                        </ul>
                    </div>
                    <div class="logo-element">
                        IN+
                    </div>
                </li>
                <li >
                    <a href="homepage.jsp"><i class="fa fa-th-large"></i> <span class="nav-label">主页</span></a>
                </li>
                <li>
                    <a href="datatable.jsp"><i class="fa fa-th-large"></i> <span class="nav-label">表格</span></a>
                </li>
                <li>
                    <a href="graph_flot.jsp"><i class="fa fa-bar-chart-o"></i> <span class="nav-label">flot图表</span> </a>
                </li>
                <li class="active">
                    <a href="answer.jsp"><i class="fa fa-diamond"></i> <span class="nav-label">简单问答</span> </a>
                </li>
                <li>
                    <a href="d3.jsp"><i class="fa fa-diamond"></i> <span class="nav-label">d3</span> </a>
                </li>
                <li>
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
                    <%--<form role="search" class="navbar-form-custom" method="post" action="#">--%>
                        <%--<div class="form-group">--%>
                            <%--<input type="text" placeholder="请输入搜索内容" class="form-control" name="top-search" id="top-search">--%>
                        <%--</div>--%>
                    <%--</form>--%>
                </div>
                <ul class="nav navbar-top-links navbar-right">
                    <li>
                        <a href="#">
                            <i class="fa fa-sign-out"></i> 退出
                        </a>
                    </li>
                </ul>

            </nav>
        </div>
        <div class="wrapper wrapper-content animated fadeInRight">
            <div class="row">
                <div class="col-lg-6">
                    <div  class="input-group">
                        <%--<input type="text" class="form-control" id="editor" placeholder="请输入问题">--%>
                        <%--<span class="input-group-btn">--%>
                            <%--<button class="btn btn-primary" type="button" id="myButton" onclick="massage()">--%>
                                <%--确认--%>
                            <%--</button>--%>

                        <%--</span>--%>
                        <form method="post" action="answer.do">
                            <input type="text" class="form-control" id="editor" name='editor' placeholder="请输入问题">
                            <span class="input-group-btn">
                                <button class="btn btn-primary" type="submit" id="myButton">
                                确认
                                </button>
                            </span>
                        </form>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-6">
                    <div>
                        <textarea class="form-control" id="output" name='output' rows="20">
                        </textarea>
                        <script>

                            var searchURL = decodeURI(window.location.search);
                            searchURL = searchURL.substring(1, searchURL.length);
                            // console.log(searchURL);
                            if(searchURL.indexOf("&") !== -1) {
                                var parmas = searchURL.split("&");
                                var targetPageId;
                                if (parmas[0]) {
                                    console.log(parmas[0]);
                                    targetPageId = parmas[0].split("=")[1];
                                    document.getElementById("editor").value = targetPageId;
                                }
                                // console.log(targetPageId);
                                if (parmas[1])
                                    console.log(parmas[1]);
                                targetPageId = parmas[1].split("=")[1];
                                document.getElementById("output").innerText = targetPageId;
                            }
                        </script>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>




<!-- 主要的脚本 -->
<script src="js/jquery-3.1.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="js/plugins/slimscroll/jquery.slimscroll.min.js"></script>

<!-- 自定义和插件javascript -->
<script src="js/inspinia.js"></script>
<script src="js/plugins/pace/pace.min.js"></script>
<script>
    function massage(){
        //var content = document.getElementById("txt");
        //alert(content.value);
        function change() {
            // document.getElementById("output").value=document.getElementById("editor").value;
            // console.log(document.getElementById("editor").value)

        }
        change();

    }

</script>

</body>
</html>

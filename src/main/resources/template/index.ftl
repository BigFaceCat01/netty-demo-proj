<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>shell</title>
    <script src="https://lib.sinaapp.com/js/jquery/2.0.2/jquery-2.0.2.min.js">
    </script>
</head>

<body>
<textarea width="500" height="400" style="overflow: auto" id="shell_box">login success </textarea>
<br/>
<input id="command" />
<button onclick="javascript:btn()">click</button>
<script>

    function btn(){
        var url = "http://localhost:19090/?command=" + $("#command").val();
        $.ajax({
            headers: {
                "Content-type": "text/plain; charset=utf-8"
            },
            type: "GET",
            url: url,
            success: function (res) {
                var va = $("#shell_box").val();

                $("#shell_box").val(va+res);
            }
        });
    }


</script>
</body>
</html>
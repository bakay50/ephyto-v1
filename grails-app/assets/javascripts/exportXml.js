/**
 * Created by a.goya on 8/26/2016.
 */

function initExportXMLFunction() {
    $(".exportXMLButton").click(function (e) {
        e.preventDefault();
        var url = $("#exportXmlUrl").val();
        var type = $(this).hasClass("etGen") ? "etGen" : "ephytoGen";
        var dataToSend = $("#appMainForm").serializeArray();
        dataToSend.push({name: 'domainName', value: type});
        var hiddenForm = $("<form></form>")
            .attr("method","POST")
            .attr("action",url).hide();
        $.each(dataToSend,function(idx,item){
            var hiddenField = document.createElement("input");
            hiddenField.setAttribute("type", "hidden");
            hiddenField.setAttribute("name", item["name"]);
            hiddenField.setAttribute("value", item["value"]);
            hiddenForm.append(hiddenField);
        });
        document.body.appendChild(hiddenForm[0]);
        hiddenForm[0].submit();
    });
}
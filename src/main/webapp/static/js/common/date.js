$(function () {
    $.datetimepicker.setLocale('ch');//设置中文
    var date = new Date();
    $('.my-date').datetimepicker({
        timepicker: false,
        format: "Y-m-d",
        maxDate: new Date(),
        dayOfWeekStart: 1
    });
})
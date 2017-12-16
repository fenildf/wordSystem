$(function () {
    $(".resource-edit").validate({
        rules: {
            name: {
                required: true
            },
            type: {
                required: true
            },
            url: {
                required: true
            },
            permission: {
                required: true
            }
        },
        messages: {
            name: {
                required: "请填写资源名称"
            },
            type: {
                required: "请填写资源类型"
            },
            url: {
                required: "请填写资源链接"
            },
            permission: {
                required: "请填写资源权限"
            }
        }
    });
});

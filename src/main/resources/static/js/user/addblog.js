var E = window.wangEditor;
var editor = new E('#editor');
var $blogText = $('#blogText');

editor.customConfig.onchange = function (html) {
    // 监控变化，同步更新到 textarea
    $blogText.val(html)
};
// 配置服务器端地址
editor.customConfig.uploadImgServer = '/qgyblog/user/img/add';
// 将图片大小限制为 3M
editor.customConfig.uploadImgMaxSize = 3 * 1024 * 1024;
// 限制一次最多上传 5 张图片
editor.customConfig.uploadImgMaxLength = 5
// 参数名
editor.customConfig.uploadFileName = 'imgFiles';
editor.create();
// 初始化 textarea 的值
$blogText.val(editor.txt.html());
const JS_TAG = 'wangwiejun '

var dj = {};

dj.callbacks = {};
dj.addCallback = function(name,func){
    delete dj.callbacks[name];
    dj.callbacks[name] = {callback:func};
};

dj.callbackname = function(){
    return "djapi_callback_" + (new Date()).getTime() + "_" + Math.floor(Math.random() * 10000);
};

dj.post = function(cmd,para){
    console.log(JS_TAG + 'cmd:'+cmd + ', para:'+para);
    window.injectedObject.post(cmd,JSON.stringify(para));
}

dj.add_sync = function(num1, num2){
    /*while(true) {
        // 运行在ui thread, 长时间会ANR
        console.log(JS_TAG + 'num1:'+num1 + ', num2:'+num2);
    }*/
    console.log(JS_TAG + 'num1:'+num1 + ', num2:'+num2);
    var result = window.injectedObject.add_sync(num1,num2);
    console.log(JS_TAG + 'result:'+result);
}

dj.add_async = function(num1, num2, callback){
    var callbackname = dj.callbackname();
    dj.addCallback(callbackname,callback);
    console.log(JS_TAG + 'num1:'+num1 + ', num2:'+num2 + ",生成的标识符与function映射保存起来callbackname: " + callbackname);
    window.injectedObject.add_async(num1,num2, callbackname);
}

dj.invokeJavaMethod = function(json){
//    var callbackname = dj.callbackname();
//    dj.addCallback(callbackname,callback);
        console.log(JS_TAG + 'json = ' + json);
    var result = window.injectedObject.invokeJavaMethod(json);
    console.log(JS_TAG + result);
}

dj.callback = function(para) {
    console.log(JS_TAG + 'para:'+JSON.stringify(para))
    console.log(JS_TAG + 'para.title:'+para.title)
    console.log(JS_TAG + 'para.re:'+para.re)
    console.log(JS_TAG + 'para.callBackName:'+para.callBackName)
     if (para.callBackName) {
        var xx =  dj.callbacks[para.callBackName]
        console.log(JS_TAG + 'para.callBackName:'+JSON.stringify(xx));
        if (xx.callback) {
            console.log(JS_TAG + 'para.callBackName:'+JSON.stringify(xx.callback));
            xx.callback(para.re)
        }
     }
}
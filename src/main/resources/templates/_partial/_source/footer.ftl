<!-- Import JS -->
<script>lsloader.js("lazy_js","js/lazyload.min.js")</script>
<script>lsloader.js("fontawesome_all_js", "vendor/fontawesome/js/all.min.js")</script>
<script>lsloader.js("main_js", "js/main.js")</script>
<#--<script src="js/main.js"></script>-->


<script type="text/ls-javascript" id="lazy-load">
    var myLazyLoad = new LazyLoad({
        elements_selector: ".lazy"
    });
    // Start Queue
    window.onload = function() {
        <#--queue.startDebug();-->
        setInterval(function(){
            queue.execNext();
        },200);
    };
</script>

<script>
    !function(){for(var r=document.querySelectorAll('script[type="text/ls-javascript"]'),t=0;t<r.length;t++){var e=r[t];lsloader.runInlineScript(e.id,e.id)}}();
</script>
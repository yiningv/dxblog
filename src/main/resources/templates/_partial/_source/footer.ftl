<!-- Import JS -->
<script>lsloader.js("lazy_js","/vendor/js/lazyload.min.js")</script>

<script type="text/ls-javascript" id="lazy-load">
    var myLazyLoad = new LazyLoad({
        elements_selector: ".lazy"
    });
    // Start Queue
    window.onload = function() {
        setInterval(function(){
            queue.execNext();
        },200);
    };
</script>
<!DOCTYPE html>
<html>
<#include "_partial/head.ftl">
<body>
<div class="container">
    <div class="columns">
        <div class="column is-9">
            <#include "_partial/header.ftl">
            <#include "${body}.ftl">
            <#include "_partial/footer.ftl">
        </div>
        <div class="column is-3 is-hidden-mobile">
            <div>
                <img src="">
            </div>
            <div>
                <ul>
                    <li><a href="/" class="icon-github"><i class="fab fa-lg fa-github"></i></li>
                </ul>
            </div>
        </div>
    </div>
</div>
</body>
</html>
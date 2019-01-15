<section class="section">
    <div class="container">
        <div class="columns">
            <div class="column is-3">
                <aside class="menu">
                    <div class="card">
                        <div class="card-content">
                            <form action="//www.google.com/search" target="_blank" class="form-search">
                                <input type="search" class="input">
                            </form>
                        </div>
                    </div>
                </aside>
            </div>
            <div class="column is-9">
                <div class="content">
                    <#list demos as demo>
                        <div class="card">
                            <div class="card-content">
                                ${demo}
                            </div>
                        </div>
                    </#list>
                </div>
            </div>
        </div>
    </div>
</section>
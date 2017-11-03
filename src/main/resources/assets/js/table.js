/**
 * Created by lenovo on 2017/10/30.
 */

(function ($) {
    var settings = {
        title: "",
        columns: [{title: "1",name:"a"},{title: "2",name:"b"},{title: "3",name:"c"},{title: "4",name:"d"}],
        async: false,
        page: 2,
        rows: 5,
        total: 10,
        totalPage: 2,
        url: "",
        data: [
            {a: "1a", b:"1b", c:"1c", d:"1d"},
            {a: "2a", b:"2b", c:"2c", d:"2d"},
            {a: "3a", b:"3b", c:"3c", d:"3d"},
            {a: "4a", b:"4b", c:"4c", d:"4d"},
            {a: "5a", b:"5b", c:"5c", d:"5d"},
            {a: "6a", b:"6b", c:"6c", d:"6d"},
            {a: "7a", b:"7b", c:"7c", d:"7d"},
            {a: "8a", b:"8b", c:"8c", d:"8d"}
        ],
        paginator: null,
        pre: "<i class='fa fa-angle-left' aria-hidden='true'></i>pre",
        next: "<i class='fa fa-angle-right' aria-hidden='true'></i>next",
    };

    var methods = {
        init: function (options) {
            settings = $.extend(settings, options);
            console.log(settings);
            if (settings.url != "") {
                $.ajax({
                    url: settings.url,
                    data: "page=" + settings.page + "&rows=" + settings.rows,
                    type: "get",
                    async: false,
                    success: function (reply) {
                        if(reply != null) {
                            settings.data = reply;
                        }
                    }
                })
            }

            settings.total = settings.data.length;
            settings.totalPage = Math.ceil(settings.total / settings.rows);
            if(settings.page <= 0) settings.page = 1;
            if(settings.page > settings.totalPage) settings.page = settings.totalPage;

            methods["genereateTable"].apply(this);
        },
        genereateTable: function () {
            this.empty();
            var tableBox = $("<div class='table-box'></div>");
            var table = $("<table class='table table-bordered '></table>");
            var thead = $("<thead class='table-thead'></thead>");
            var tbody = $("<tbody class='table-tbody'></tbody>");
            var headTr = $("<tr></tr>");
            $.each(settings.columns, function (i, column) {
                $("<td>" + column.title + "</td>").appendTo(headTr);
            });

            headTr.appendTo(thead);

            var startIndex = (settings.page - 1) * settings.rows;
            if(startIndex < 0 ) startIndex = 0;
            var endIndex = startIndex + settings.rows;
            var data = settings.data;
            for(var i = startIndex; i < endIndex && i<data.length; i++){
                var bodyTr = $("<tr></tr>");
                $.each(settings.columns, function (j, column) {
                    $("<td>" + data[i][column.name] + "</td>").appendTo(bodyTr);
                });
                bodyTr.appendTo(tbody);
            }

            if(settings.paginator != null && $("#" + settings.paginator).length >= 1){
                var paginator = $("<div class='paginator'></div>");
                if(settings.page > 1){
                    $("<span class='page-icon pre'>" + settings.pre + "</span>").appendTo(paginator);
                }

                var startPage = settings.page - 5 > 0 ? settings.page - 5 : 1;
                var endPage = settings.totalPage - settings.page > 5 ? settings.page + 5 : settings.totalPage;
                for(var i = startPage; i<= endPage; i++){
                    $("<span class='page-icon middle'>" + i + "</span>").appendTo(paginator);
                }

                if(settings.page < settings.totalPage){
                    $("<span class='page-icon next'>" + settings.next + "</span>").appendTo(paginator);
                }

                paginator.appendTo($("#" + settings.paginator));
            }

            thead.appendTo(table);
            tbody.appendTo(table);
            table.appendTo(tableBox);
            tableBox.appendTo(this);
        }
    };

    $.fn.tablePlugin = function (method) {
        if (methods[method]) {
            return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
        } else if (typeof method === 'object' || !method) {
            return methods.init.apply(this, arguments);
        } else {
            $.error('Method' + method + 'does not exist on jQuery.tooltip');
        }
    };
})(jQuery);
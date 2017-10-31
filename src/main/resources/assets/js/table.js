/**
 * Created by lenovo on 2017/10/30.
 */

(function ($) {
    var settings = {
        title: "",
        columns: [{title: "1",name:"a"},{title: "2",name:"b"},{title: "3",name:"c"},{title: "4",name:"d"}],
        async: false,
        page: 1,
        rows: 10,
        url: "",
        data: [
            {a: "1a", b:"1b", c:"1c", d:"1d"},
            {a: "2a", b:"2b", c:"2c", d:"2d"},
            {a: "3a", b:"3b", c:"3c", d:"3d"}
        ],
        paginator: null
    };

    var methods = {
        init: function (options) {
            settings = $.extend(options, settings);
            if (settings.url != "") {
                $.ajax({
                    url: settings.url,
                    data: "page=" + settings.page + "&rows=" + settings.rows,
                    type: "get",
                    async: false,
                    success: function (reply) {
                        if(reply != null) settings.data = reply;
                    }
                })
            }
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

            $.each(settings.data, function (i, value) {
                var bodyTr = $("<tr></tr>");
                $.each(settings.columns, function (j, column) {
                    $("<td>" + value[column.name] + "</td>").appendTo(bodyTr);
                });
                bodyTr.appendTo(tbody);
            });

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
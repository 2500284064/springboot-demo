/**
 * Created by lenovo on 2017/10/30.
 */

;(function ($) {

    var Table = function(ele, opts){
        this.$element = ele;
        this.defaultSettings = {
            title: "",
            height: "300",
            columns: [{title: "1",name:"a"},{title: "2",name:"b"},{title: "3",name:"c"},{title: "4",name:"d"}],
            async: false,
            page: 2,
            rows: 3,
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
            next: "next<i class='fa fa-angle-right' aria-hidden='true'></i>",
        };

        this.options = $.extend({}, this.defaultSettings, opts);

        var that = this;

        this.setPage = function (page) {
            this.options.page = page;
            if(that.options.page <= 0) that.options.page = 1;
            if(that.options.page > that.options.totalPage) that.options.page = that.options.totalPage;
        }

        this.generateTable = function () {
            that.$element.empty();
            var boxStyle = "height: " + that.options.height + "px";
            var tableBox = $("<div class='table-box' style='" + boxStyle + "'></div>");
            var table = $("<table class='table table-bordered '></table>");
            var thead = $("<thead class='table-thead'></thead>");
            var tbody = $("<tbody class='table-tbody'></tbody>");
            var headTr = $("<tr></tr>");
            $.each(that.options.columns, function (i, column) {
                $("<td>" + column.title + "</td>").appendTo(headTr);
            });

            headTr.appendTo(thead);

            var startIndex = (that.options.page - 1) * that.options.rows;
            if(startIndex < 0 ) startIndex = 0;
            var endIndex = startIndex + that.options.rows;
            var data = that.options.data;
            for(var i = startIndex; i < endIndex && i<data.length; i++){
                var bodyTr = $("<tr></tr>");
                $.each(that.options.columns, function (j, column) {
                    $("<td>" + data[i][column.name] + "</td>").appendTo(bodyTr);
                });
                bodyTr.appendTo(tbody);
            }

            thead.appendTo(table);
            tbody.appendTo(table);
            table.appendTo(tableBox);
            tableBox.appendTo(that.$element);
        }

        this.generatePaginator = function () {
            if(that.options.paginator != null && $("#" + that.options.paginator).length >= 1){
                $("#" + that.options.paginator).empty();
                var paginator = $("<div class='paginator'></div>");
                if(that.options.page > 1){
                    $("<span class='page-icon pre'>" + that.options.pre + "</span>").appendTo(paginator);
                }

                var startPage = that.options.page - 5 > 0 ? that.options.page - 5 : 1;
                var endPage = that.options.totalPage - that.options.page > 5 ? that.options.page + 5 : that.options.totalPage;
                for(var i = startPage; i<= endPage; i++){
                    var pageBtn = $("<span class='page-icon middle'>" + i + "</span>");
                    if(i == that.options.page) pageBtn.addClass("selected");
                    pageBtn.appendTo(paginator);
                }

                if(that.options.page < that.options.totalPage){
                    $("<span class='page-icon next'>" + that.options.next + "</span>").appendTo(paginator);
                }

                paginator.appendTo($("#" + that.options.paginator));

                $("#" + that.options.paginator + " .paginator .page-icon.pre").on('click', function(){
                    that.prePage();
                });

                $("#" + that.options.paginator + " .paginator .page-icon.next").on('click', function(){
                    that.nextPage();
                })

                $("#" + that.options.paginator + " .paginator .page-icon.middle").on('click', function(){
                    that.setPage(parseInt($(this).html()));
                    that.flushData();
                    that.generateTable();
                    that.generatePaginator();
                })
            }
        }
        
        this.prePage = function () {
            that.options.page --;
            that.flushData();
            that.generateTable();
            that.generatePaginator();
        }

        this.nextPage = function () {
            that.options.page ++;
            that.flushData();
            that.generateTable();
            that.generatePaginator();
        }
        
        this.flushData = function () {
            if (that.options.url != "") {
                $.ajax({
                    url: that.options.url,
                    data: "page=" + that.options.page + "&rows=" + that.options.rows,
                    type: "get",
                    async: false,
                    success: function (reply) {
                        if(reply != null) {
                            that.options.data = reply.data;
                            that.options.total = reply.total;
                        }
                    }
                })
            }else{
                that.options.total = that.options.data.length;
            }

            that.options.totalPage = Math.ceil(that.options.total / that.options.rows);
            if(that.options.page <= 0) that.options.page = 1;
            if(that.options.page > that.options.totalPage) that.options.page = that.options.totalPage;
        }
    }

    Table.prototype = {
        init: function () {
            this.flushData();
            this.generateTable();
            this.generatePaginator();
        },
        
        prePage: function () {
            this.prePage();
        },

        nextPage: function () {
            this.nextPage();
        },

        skipToPage: function (page) {
            this.setPage(page);
            this.flushData();
            this.generateTable();
            this.generatePaginator();
        },

        refresh: function () {
            this.flushData();
            this.generateTable();
            this.generatePaginator();
        }
    }

    $.fn.tablePlugin = function (method) {
        if (Table.prototype.hasOwnProperty(method)) {
            var table = new Table(this, Array.prototype.slice.call(arguments, 1)[0]);
            Table.prototype[method].apply(table)
        } else if (typeof method === 'object' || !method) {
            var table = new Table(this, arguments);
            return table.init();
        } else {
            $.error('Method' + method + 'does not exist on jQuery.tooltip');
        }
    };

})(jQuery);
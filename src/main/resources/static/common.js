$(function () {
    $.cxCalendar.defaults.endDate = 2050;
    dayjs.extend(window.dayjs_plugin_isoWeek);
    $(document).ajaxError(function (event, request, settings) {
        if (request.responseJSON) {
            alert('发生错误:' + request.responseJSON.message);
        }
    })

})


function post(url, params) {
    const tempForm = document.createElement("form");
    tempForm.action = url;
    tempForm.target = "_self";
    tempForm.method = "post";
    tempForm.style.display = "none";
    for (let x in params) {
        const opt = document.createElement("textarea");
        opt.name = x;
        opt.value = params[x];
        tempForm.appendChild(opt);
    }
    document.body.appendChild(tempForm);
    tempForm.submit();

}

function initDeleteAll(url) {
    $('#btn-delete').on('click', function () {
        if (!confirm('确认删除选中记录吗？')) {
            return false;
        }
        let ids = [];
        $('[name="id"]:checked').each(function (i, t) {
            ids.push($(t).val());
        });
        doDelete(url, {id: ids});
    });

    $('.btn-delete').on('click', function () {
        if (!confirm('确认删除选中记录吗？')) {
            return false;
        }
        let id = $(this).data('id');
        doDelete(url, {id: id});
    });

    function doDelete(url, data) {
        $.ajax({
            url: url,
            data: data,
            type: 'post',
            dataType: 'json'
        }).done(function (respdata) {
            if (respdata.resultCode == '0') {
                window.location.reload();
            } else {
                alert('发生错误');
            }
        });
    }
}

function initDelete(selector, $parent) {
    selector = selector || '.btn-delete'
    $parent = $parent || $(document);
    $parent.find(selector).on('click', function (e) {
        if (!confirm('确认删除选中记录吗？')) {
            return false;
        }
        const url = $(this).data('url') || $(this).attr('href');
        if ($(this).is('a')) {
            e.preventDefault();
        }
        const data = $(this).removeData('url').data();
        $.ajax({
            url: url,
            type: 'post',
            data: data,
            dataType: 'json'
        }).done(function (respdata) {
            if (respdata.resultCode == '0') {
                window.location.reload();
            } else {
                alert('发生错误:' + respdata.message);
            }
        });
    });

}

function initView(selector, $parent, callback) {
    if ($.isFunction(selector)) {
        callback = selector;
        selector = '.btn-view';
        $parent = $(document);
    } else {
        selector = selector || '.btn-view';
        $parent = $parent || $(document);
    }
    $parent.find(selector).on('click', function (e) {
        const url = $(this).data('url') || $(this).attr('href');
        if ($(this).is('a')) {
            e.preventDefault();
        }
        const data = $(this).removeData('url').data();
        $.ajax({
            url: url,
            data: data,
            type: 'get'
        }).done(function (respdata, textStatus) {
            console.log(textStatus)
            let $html = $(respdata);
            let $modal = new bootstrap.Modal($html);
            if ($.isFunction(callback)) {
                callback.call(this, $modal, $html);
            }
            $modal.show();
            $html.on('hidden.bs.modal', function (e) {
                $html.remove();
            });
        })
    });
}

function initAction(selector, $parent) {
    selector = selector || '.btn-action';
    $parent = $parent || $(document);
    $parent.find(selector).on('click', function (e) {
        const message = $(this).text() || '操作';
        if (!confirm('确认' + message + '选中记录吗？')) {
            return false;
        }
        const url = $(this).data('url') || $(this).attr('href');
        if ($(this).is('a')) {
            e.preventDefault();
        }
        const data = $(this).removeData('url').data();
        $.ajax({
            url: url,
            type: 'post',
            data: data,
            dataType: 'json'
        }).done(function (respdata) {
            if (respdata.resultCode == 0) {
                window.location.reload();
            } else {
                alert('发生错误:' + respdata.message);
            }
        });
    });
}

function initForm(selector, $parent, callback) {
    if ($.isFunction(selector)) {
        callback = selector;
        selector = '.btn-form';
        $parent = $(document);
    } else {
        selector = selector || '.btn-form';
        $parent = $parent || $(document);
    }
    $parent.find(selector).on('click', function (e) {
        const url = $(this).data('url') || $(this).attr('href');
        if ($(this).is('a')) {
            e.preventDefault();
        }
        const data = $(this).removeData('url').data();
        $.ajax({
            url: url,
            data: data,
            type: 'get'
        }).done(function (respdata) {
            let $html = $(respdata);
            let $modal = new bootstrap.Modal($html);
            if ($.isFunction(callback)) {
                callback.call(this, $modal, $html);
            }
            onShownModal($modal, $html);
            $modal.show();
            $html.on('hidden.bs.modal', function (e) {
                $html.remove();
            });
        });
    });
}

function initAutocomplete(selector, url) {
    const option = {
        theme: 'bootstrap-5',
        language: 'zh-CN',
        placeholder: '请选择',
        ajax: {
            url: url,
            data: function (params) {
                return {
                    keyword: params.term,
                    pageNumber: params.page || 1
                }
            },
            delay: 250,
            processResults: function (respdata, params) {
                let results = [], more = false, data = respdata.data;
                if (respdata.resultCode == 0) {
                    for (let i = 0; i < data.records.length; i++) {
                        let item = data.records[i];
                        results.push({id: item.key, text: item.label});
                    }
                    more = !data.last;
                }
                return {
                    results: results,
                    pagination: {
                        more: more
                    }
                };
            },
        },
    }
    if (typeof selector === 'string') {
        $(selector).select2(option);
    } else {
        selector.select2(option);
    }
}

function initAutoFill(selector) {
    let $dom = selector || $('.btn-load');
    if (typeof selector === 'string') {
        $dom = $(selector)
    }
    $dom.on('click', function () {
        const url = $(this).data('url');
        const $form = $(this).closest('form');
        const data = {};
        $form.find('[data-find-prop]').each(function (i, t) {
            let prop = $(t).data('find-prop');
            data[prop] = $(t).val();
        });
        $.ajax({
            url: url,
            data: data,
            type: 'get',
            dataType: 'json'
        }).done(function (respdata) {
            if (respdata.resultCode == '0') {
                if (respdata.data == null) {
                    alert('未调取到符合要求的记录');
                } else {
                    $form.find('[data-fill-prop]').each(function (i, t) {
                        let prop = $(t).data('fill-prop');
                        $(t).val(respdata.data[prop]);
                    });
                }

            } else {
                alert('发生错误:' + respdata.message);
            }
        });
    });

}

function onShownModal($modal, $html) {
    const $form = $html.find('form');
    const url = $form.attr('action');
    $html.on('click', '.btn-save', function () {
        if (!$form[0].reportValidity()) {
            return false;
        }
        let options = {
            data: $form.serialize(),
        }
        if ($form.attr('enctype') == 'multipart/form-data') {
            options = {
                data: new FormData($form[0]),
                processData: false,
                contentType: false
            }
        }
        options = $.extend({
            url: url,
            dataType: 'json',
            type: 'post'
        }, options)
        $.ajax(options).done(function (respdata) {
            if (respdata.resultCode == '0') {
                window.location.reload();
            } else {
                alert('发生错误:' + respdata.message);
            }
        });
    });

}
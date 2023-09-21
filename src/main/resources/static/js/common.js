var check = 0;

function dailycheck(f, e, d) {
    if (e < 13 && e != 0 && d != 0) {
        if (e == 2) {
            if (d > 29) {
                return false
            } else {
                if (d == 29) {
                    if ((f % 4 != 0) || (f % 100 == 0) && (f % 400 != 0)) {
                        return false
                    }
                }
            }
            if (d >= 1) {
                return true
            } else {
                return false
            }
        } else {
            if (e == 4 || e == 6 || e == 9 || e == 11) {
                if (d >= 1 && d <= 30) {
                    return true
                } else {
                    return false
                }
            } else {
                if (d >= 1 && d <= 31) {
                    return true
                } else {
                    return false
                }
            }
        }
    } else {
        return false
    }
}

function digit_check(d) {
    var c = d.which ? d.which : event.keyCode;
    if (c == 8 || c == 9 || c == 37 || c == 39 || c == 46) {
        return true
    }
    if (!(c >= 48 && c <= 57) && !(c >= 96 && c <= 105)) {
        return false
    }
}

function fn_press_han(b) {
    if (event.keyCode == 8 || event.keyCode == 9 || event.keyCode == 37 || event.keyCode == 39 || event.keyCode == 46) {
        return true
    }
    b.value = b.value.replace(/[\ㄱ-ㅎㅏ-ㅣ가-힣]/g, "")
}

function leadingZeros(e, f) {
    var d = "";
    e = e.toString();
    if (e.length < f) {
        for (i = 0; i < f - e.length; i++) {
            d += "0"
        }
    }
    return d + e
}

function create_form(k, j, g, f) {
    var h = document.createElement("form");
    h.name = k;
    h.method = j;
    h.action = g;
    h.target = f;
    return h
}

function add_input(h, f, g) {
    var e = document.createElement("input");
    e.type = "hidden";
    e.name = f;
    e.value = g;
    h.insertBefore(e, null);
    return h
}

function setComma(c) {
    c = "" + c + "";
    var d = "";
    for (i = 0; i < c.length; i++) {
        if (i > 0 && (i % 3) == 0) {
            d = c.charAt(c.length - i - 1) + "," + d
        } else {
            d = c.charAt(c.length - i - 1) + d
        }
    }
    return d
}

function setCommaMinus(e) {
    var d = false;
    if (e.substring(0, 1) == "-") {
        e = e.substring(1, e.length);
        d = true
    }
    e = "" + e + "";
    var f = "";
    for (i = 0; i < e.length; i++) {
        if (i > 0 && (i % 3) == 0) {
            f = e.charAt(e.length - i - 1) + "," + f
        } else {
            f = e.charAt(e.length - i - 1) + f
        }
    }
    if (d) {
        return "-" + f
    } else {
        return f
    }
}

function isDateFormat(c) {
    var d = /[0-9]{4}-[0-9]{2}-[0-9]{2}/;
    return c.match(d)
}

function phonecheck(c) {
    var d = /^(01[016789]{1}|02|0[3-9]{1}[0-9]{1})-?[0-9]{3,4}-?[0-9]{4}$/;
    if (c == "" && c == null) {
        return false
    } else {
        if (!d.test(c)) {
            return false
        }
    }
    return true
}

function regrex(f, e, d) {
    e = e.replace(d, "");
    f.val(e)
}

function reqparam(f) {
    var k = f.split("&");
    var g = new Array();
    for (var j = 0; j < k.length; j++) {
        var h = k[j].split("=");
        g.push(h)
    }
    return g
}

function getNum(b) {
    if (isNaN(b)) {
        return 0
    } else {
        return b
    }
}

function detailview(e, g) {
    cw = screen.availWidth;
    ch = screen.availHeight;
    sw = 1200;
    sh = 800;
    ml = (cw - sw) / 2;
    ml += window.screenLeft;
    mt = (ch - sh) / 2;
    var f = window.open("", "popup_printss", "width=" + sw + ",height=" + sh + ",top=" + mt + ",left=" + ml + ",resizable=no,scrollbars=yes");
    var h = create_form("formss", "POST", "/f2f/02/detailview", "popup_printss");
    h = add_input(h, "DONOR_NO_INS", e);
    h = add_input(h, "GIFT_NO_INS", g);
    h = add_input(h, "_csrf", $("#_csrf").val());
    document.body.insertBefore(h, null);
    h.submit();
    $("form[name='formss']").remove()
}

function initBase64() {
    enc64List = new Array();
    dec64List = new Array();
    var b;
    for (b = 0; b < 26; b++) {
        enc64List[enc64List.length] = String.fromCharCode(65 + b)
    }
    for (b = 0; b < 26; b++) {
        enc64List[enc64List.length] = String.fromCharCode(97 + b)
    }
    for (b = 0; b < 10; b++) {
        enc64List[enc64List.length] = String.fromCharCode(48 + b)
    }
    enc64List[enc64List.length] = "+";
    enc64List[enc64List.length] = "/";
    for (b = 0; b < 128; b++) {
        dec64List[dec64List.length] = -1
    }
    for (b = 0; b < 64; b++) {
        dec64List[enc64List[b].charCodeAt(0)] = b
    }
}

var Base64 = {
    _keyStr: "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=", encode: function (u) {
        var m = "";
        var n, p, r, o, q, s, t;
        var l = 0;
        u = Base64._utf8_encode(u);
        while (l < u.length) {
            n = u.charCodeAt(l++);
            p = u.charCodeAt(l++);
            r = u.charCodeAt(l++);
            o = n >> 2;
            q = ((n & 3) << 4) | (p >> 4);
            s = ((p & 15) << 2) | (r >> 6);
            t = r & 63;
            if (isNaN(p)) {
                s = t = 64
            } else {
                if (isNaN(r)) {
                    t = 64
                }
            }
            m = m + this._keyStr.charAt(o) + this._keyStr.charAt(q) + this._keyStr.charAt(s) + this._keyStr.charAt(t)
        }
        return m
    }, decode: function (u) {
        var m = "";
        var n, p, r;
        var o, q, s, t;
        var l = 0;
        u = u.replace(/[^A-Za-z0-9\+\/\=]/g, "");
        while (l < u.length) {
            o = this._keyStr.indexOf(u.charAt(l++));
            q = this._keyStr.indexOf(u.charAt(l++));
            s = this._keyStr.indexOf(u.charAt(l++));
            t = this._keyStr.indexOf(u.charAt(l++));
            n = (o << 2) | (q >> 4);
            p = ((q & 15) << 4) | (s >> 2);
            r = ((s & 3) << 6) | t;
            m = m + String.fromCharCode(n);
            if (s != 64) {
                m = m + String.fromCharCode(p)
            }
            if (t != 64) {
                m = m + String.fromCharCode(r)
            }
        }
        m = Base64._utf8_decode(m);
        return m
    }, _utf8_encode: function (c) {
        c = c.replace(/\r\n/g, "\n");
        var f = "";
        for (var g = 0; g < c.length; g++) {
            var h = c.charCodeAt(g);
            if (h < 128) {
                f += String.fromCharCode(h)
            } else {
                if ((h > 127) && (h < 2048)) {
                    f += String.fromCharCode((h >> 6) | 192);
                    f += String.fromCharCode((h & 63) | 128)
                } else {
                    f += String.fromCharCode((h >> 12) | 224);
                    f += String.fromCharCode(((h >> 6) & 63) | 128);
                    f += String.fromCharCode((h & 63) | 128)
                }
            }
        }
        return f
    }, _utf8_decode: function (f) {
        var c = "";
        var h = 0;
        var g = c1 = c2 = 0;
        while (h < f.length) {
            g = f.charCodeAt(h);
            if (g < 128) {
                c += String.fromCharCode(g);
                h++
            } else {
                if ((g > 191) && (g < 224)) {
                    c2 = f.charCodeAt(h + 1);
                    c += String.fromCharCode(((g & 31) << 6) | (c2 & 63));
                    h += 2
                } else {
                    c2 = f.charCodeAt(h + 1);
                    c3 = f.charCodeAt(h + 2);
                    c += String.fromCharCode(((g & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
                    h += 3
                }
            }
        }
        return c
    }
};

function tabControl(a) {
    var e = $(a);
    var f = e.parent().parent().parent().parent().parent().next();
    if (f.is(":visible")) {
        f.slideUp(100);
        $(a).css({background: "#7dc679"})
    } else {
        f.slideDown(100);
        $(a).css({background: "#ce5f60"})
    }
}

function keyCheck(b) {
    if ((b.keyCode >= 65 && b.keyCode <= 90) || (b.keyCode >= 48 && b.keyCode <= 57) || (b.keyCode >= 37 && b.keyCode <= 40) || (b.keyCode >= 96 && b.keyCode <= 105) || b.keyCode == 8 || b.keyCode == 9 || b.keyCode == 45 || b.keyCode == 46) {
        return true
    } else {
        return false
    }
}

function keyCheckPwd(b) {
    if ((b.keyCode >= 106 && b.keyCode <= 109) || (b.keyCode == 187) || (b.keyCode >= 189 && b.keyCode <= 192) || (b.keyCode == 61) || (b.keyCode == 13)) {
        return true
    } else {
        return false
    }
};


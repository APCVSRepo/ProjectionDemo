var curElement = null;

window.onclick = function()
{
    curElement = event.target;

    if (curElement.nodeName.toLowerCase() == "input" && !curElement.readOnly && (
        curElement.type == "text" || curElement.type == "password" || curElement.type == "number" ||
        curElement.type == "url" || curElement.type == "search" || curElement.type == "textarea"))
    {
//        if (curElement.id == null || curElement.id == "")
//            curElement.id = uuid();

        window.callback.onInput(curElement.id);
    }
};

function uuid()
{
    var s = [];
    var hexDigits = "0123456789abcdef";
    for (var i = 0; i < 36; i++)
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);

    s[14] = "4";
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);
    s[8] = s[13] = s[18] = s[23] = "-";

    return s.join("");
};

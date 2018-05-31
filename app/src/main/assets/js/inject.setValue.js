function inject_setValue(value)
{
    if (curElement != null && !curElement.readOnly)
        curElement.value = value;
};
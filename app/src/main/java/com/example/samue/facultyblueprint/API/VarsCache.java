package com.example.samue.facultyblueprint.API;

import android.widget.CheckBox;

import java.util.Dictionary;

//
//
//
//
//
//
//
// That's basically UI so it's not so necessary for now
public class VarsCache
{
    private Dictionary<String, String> cache;
    private Dictionary<TextBox, String> textbox_binding;
    private Dictionary<CheckBox, String> checkbox_binding;
//    private Dictionary<String, String> cache = new Dictionary<String, String>();
//    private Dictionary<TextBox, String> textbox_binding = new Dictionary<TextBox, String>();
//    private Dictionary<CheckBox, String> checkbox_binding = new Dictionary<CheckBox, String>();

    public String GetVar(String varName, String default_value)
    {
        if (cache.ContainsKey(varName))
            return cache[varName];
        return default_value;
    }
    public void SetVar(String varName, String value)
    {
        cache[varName] = value;
    }
    public void BindWithTextBox(String varName, TextBox textbox)
    {
        textbox.Text = this.GetVar(varName, textbox.Text);
        textbox.TextChanged += new TextChangedEventHandler(textbox_TextChanged);
        this.textbox_binding.Add(textbox, varName);
    }

    void textbox_TextChanged(object sender, TextChangedEventArgs e)
    {
        TextBox textbox = (TextBox)e.Source;
        String varName = this.textbox_binding[textbox];
        SetVar(varName, textbox.Text);
    }

    public void BindWithCheckBox(String varName, CheckBox checkbox)
    {
        checkbox.IsChecked = this.GetVar(varName, checkbox.IsChecked == true ? "true" : "false") == "true";
        //checkbox.Click += new RoutedEventHandler(checkbox_Click);
        checkbox.Checked += new RoutedEventHandler(checkbox_Click);
        checkbox.Unchecked += new RoutedEventHandler(checkbox_Click);
        this.checkbox_binding.add(checkbox, varName);
    }

    void checkbox_Click(object sender, RoutedEventArgs e)
    {
        CheckBox checkbox = (CheckBox)e.Source;
        String varName = this.checkbox_binding[checkbox];
        SetVar(varName, checkbox.IsChecked == true ? "true" : "false");
    }

}

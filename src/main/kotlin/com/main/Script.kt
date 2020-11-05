package com.main

fun getScript(key: String)= """
    var findActiveExpression = "//li[contains(@class, 'active')]"

    var buttons = null
    var korrektur = document.evaluate("//a[text() = 'Korrektur']", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue


    document.evaluate("//h3[text() = 'Eingabe Ihres DiBa Keys']", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue

    function findButtonToPress( position ){
        console.log("Position: " + position)
        var key = "$key"[position]
        console.log("Key: " + key)
        return  document.evaluate("//a[text()='" + key + "']", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue

    }


    if(korrektur != null){
        var button = document.evaluate( findActiveExpression, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue
        while(button != null)
        {
            var data = button.attributes["data-wicket-path"].value
            console.log("Data: " + data)
            var position = data[data.length - 1]
            var buttonToPress = findButtonToPress(position)
            console.log("Button to press: " + buttonToPress)
            buttonToPress.click()
            button = document.evaluate( findActiveExpression, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue
        }


        var loginButton = document.evaluate( "//button[text() = 'Log-in']", document, null,  XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue
        loginButton.click()
    }
""".trimIndent()

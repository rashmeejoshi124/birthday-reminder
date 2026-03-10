package com.rashmi.birthdayreminder.ui.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class DateVisualTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {

        val trimmed = if (text.text.length >= 8) text.text.substring(0..7) else text.text

        var out = ""

        for (i in trimmed.indices) {

            out += trimmed[i]

            if (i == 1 || i == 3) out += "/"
        }

        return TransformedText(
            AnnotatedString(out),
            DateOffsetMapping()
        )
    }
}

class DateOffsetMapping : OffsetMapping {

    override fun originalToTransformed(offset: Int): Int {

        return when {
            offset <= 2 -> offset
            offset <= 4 -> offset + 1
            offset <= 8 -> offset + 2
            else -> 10
        }
    }

    override fun transformedToOriginal(offset: Int): Int {

        return when {
            offset <= 2 -> offset
            offset <= 5 -> offset - 1
            offset <= 10 -> offset - 2
            else -> 8
        }
    }
}
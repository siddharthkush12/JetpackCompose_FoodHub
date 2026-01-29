package com.example.foodapp.ui

import android.widget.Button
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodapp.R
import com.example.foodapp.ui.theme.Orange
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun GroupSocialButtons(
    color: Color=Color.White,
    onFacebookClick: () -> Unit,
    onGoogleClick: () -> Unit
) {
    Column() {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp)
        ) {
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                thickness = 1.dp,
                color = color
            )
            Spacer(modifier = Modifier.size(20.dp))
            Text(
                text = stringResource(R.string.sign_with),
                color = color,
                fontSize = 15.sp
            )
            Spacer(modifier = Modifier.size(20.dp))
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                thickness = 1.dp,
                color = color
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(0.dp,15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SocialButton(icon = R.drawable.ic_fb_icon, title = R.string.facebook) {
                onFacebookClick()
            }
            SocialButton(icon = R.drawable.ic_google_icon, title = R.string.google) {
                onGoogleClick()
            }
        }
    }
}



@Composable
fun SocialButton(
    icon:Int,
    title:Int,
    onClick: () -> Unit
){
    Button(
        onClick = onClick,
        modifier = Modifier.width(155.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 8.dp,
            pressedElevation = 12.dp
        )
    ) {
        Image(painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = stringResource(title),
            color = Color.Black,
            fontSize = 13.sp
        )
    }
}




@Composable
fun FoodHubTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource? = null,
    shape: Shape = RoundedCornerShape(10.dp),
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors().copy(
        focusedIndicatorColor = Orange,
        unfocusedIndicatorColor = Color.Gray.copy(alpha = 0.4f),
    )
){
    Column() {
        label?.let {
            Row{
                Spacer(modifier = Modifier)
                it()
            }
        }
        Spacer(modifier = Modifier.size(8.dp))
        OutlinedTextField(
            value=value,
            onValueChange=onValueChange,
            modifier=modifier,
            enabled=enabled,
            readOnly=readOnly,
            textStyle=textStyle.copy(fontWeight = FontWeight.SemiBold),
            label=null,
            placeholder=placeholder,
            leadingIcon=leadingIcon,
            trailingIcon=trailingIcon,
            prefix=prefix,
            suffix=suffix,
            supportingText=supportingText,
            isError=isError,
            visualTransformation=visualTransformation,
            keyboardOptions=keyboardOptions,
            keyboardActions=keyboardActions,
            singleLine=singleLine,
            maxLines=maxLines,
            minLines=minLines,
            interactionSource=interactionSource,
            shape=shape,
            colors=colors
        )
    }
}







/*
 * Copyright (c) 2020 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.android.jetpackcompose.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.raywenderlich.android.jetpackcompose.R
import com.raywenderlich.android.jetpackcompose.router.BackButtonHandler
import com.raywenderlich.android.jetpackcompose.router.JetFundamentalsRouter
import com.raywenderlich.android.jetpackcompose.router.Screen

@Composable
fun ExploreButtonsScreen() {
  Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(
      space = 20.dp,
      alignment = Alignment.CenterVertically
    )
  ) {

    MyButton()
    MyRadioGroup()
    MyFloatingActionButton()

    BackButtonHandler {
      JetFundamentalsRouter.navigateTo(Screen.Navigation)
    }
  }
}

@Composable
fun MyButton() {
  Button(
    onClick = { /*TODO*/ },
    colors = ButtonDefaults.buttonColors(
      backgroundColor = colorResource(id = R.color.colorPrimary),
      contentColor = Color.White
    ),
    border = BorderStroke(
      width = 1.dp,
      color = colorResource(id = R.color.colorPrimaryDark)
    ),
    shape = RoundedCornerShape(50)
  ) {
    Text(
      text = stringResource(id = R.string.button_text),
      fontWeight = FontWeight.Normal
//            color = Color.White
    )
  }
}

@Composable
fun MyRadioGroup() {
  val radioButtons = listOf("dude", "dudette", "dunno")
  val selectedButton = remember { mutableStateOf(radioButtons.first()) }

  Column(
//    Modifier.fillMaxWidth()
  ) {
    radioButtons.forEach { label ->
      val isSelected = label == selectedButton.value
      val colors = RadioButtonDefaults.colors(
        selectedColor = colorResource(id = R.color.colorAccent),
        unselectedColor = colorResource(id = R.color.colorPrimaryDark),
        disabledColor = Color.LightGray
      )

      Row(
//        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceBetween
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        RadioButton(
          colors = colors,
          selected = isSelected,
          onClick = { selectedButton.value = label }
        )
        Text(
          text = label,
          color = if (isSelected) colorResource(id = R.color.colorAccent)
          else colorResource(id = R.color.colorPrimaryDark)
        )
      }
    }
  }
}

@Composable
fun MyFloatingActionButton() {
  FloatingActionButton(
    onClick = { /*TODO*/ },
    backgroundColor = colorResource(id = R.color.colorAccent),
    contentColor = Color.White,
    content = {
      Icon(imageVector = Icons.Filled.Add)
    }
  )
}

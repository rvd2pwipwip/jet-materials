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

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.painter.Painter


import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.unit.dp
import com.raywenderlich.android.jetpackcompose.R
import com.raywenderlich.android.jetpackcompose.router.BackButtonHandler
import com.raywenderlich.android.jetpackcompose.router.JetFundamentalsRouter
import com.raywenderlich.android.jetpackcompose.router.Screen

@Composable
fun ScrollingScreen() {

  MyScrollingScreen()

  BackButtonHandler {
    JetFundamentalsRouter.navigateTo(Screen.Navigation)
  }
}

@Composable
fun MyScrollingScreen(modifier: Modifier = Modifier) {
  Column(
    modifier = modifier
      .verticalScroll(rememberScrollState())
      .padding(all = 10.dp),
    verticalArrangement = Arrangement.spacedBy(20.dp)
  ) {

//  Row(
//    modifier = modifier
//      .horizontalScroll(rememberScrollState())
//      .padding(all = 30.dp),
//    horizontalArrangement = Arrangement.spacedBy(10.dp)
//  ) {
    BookImage(
      R.drawable.advanced_architecture_android,
//      R.string.advanced_architecture_android
    )
    BookImage(
      R.drawable.kotlin_aprentice,
//      R.string.kotlin_apprentice
    )
    BookImage(
      R.drawable.kotlin_coroutines,
//      R.string.kotlin_coroutines
    )
  }
}

//@Composable
//fun BookImage(@DrawableRes imageResId: Int, @StringRes contentDescriptionResId: Int){
//  Image(
//    bitmap = ImageBitmap.imageResource(imageResId),
//    contentDescription = stringResource(contentDescriptionResId),
//    contentScale = ContentScale.FillBounds,
//    modifier = Modifier.size(476.dp, 616.dp)
//  )
//}


@Composable
fun BookImage(@DrawableRes imageResId: Int) {
  Image(
    painter = painterResource(id = imageResId),
    modifier = Modifier.clip(RoundedCornerShape(10.dp)),
    contentScale = ContentScale.Fit,
  )
}

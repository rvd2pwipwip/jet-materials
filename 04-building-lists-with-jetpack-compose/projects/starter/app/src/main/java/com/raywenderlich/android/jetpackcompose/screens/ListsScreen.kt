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
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raywenderlich.android.jetpackcompose.R
import com.raywenderlich.android.jetpackcompose.router.BackButtonHandler
import com.raywenderlich.android.jetpackcompose.router.JetFundamentalsRouter
import com.raywenderlich.android.jetpackcompose.router.Screen

data class BookCategory(@StringRes val categoryResourceId: Int, val bookImageResources: List<Int>)

private val items = listOf(
  BookCategory(
    R.string.android,
    listOf(
      R.drawable.android_aprentice,
      R.drawable.saving_data_android,
      R.drawable.advanced_architecture_android
    )
  ),
  BookCategory(
    R.string.kotlin,
    listOf(
      R.drawable.kotlin_coroutines,
      R.drawable.kotlin_aprentice
    )
  ),
  BookCategory(
    R.string.swift,
    listOf(
      R.drawable.combine,
      R.drawable.rx_swift,
      R.drawable.swift_apprentice,
    )
  ),
  BookCategory(
    R.string.ios,
    listOf(
      R.drawable.core_data,
      R.drawable.ios_apprentice,
    )
  )
)

@ExperimentalFoundationApi
@Composable
fun ListScreen() {
  MyList()

  BackButtonHandler {
    JetFundamentalsRouter.navigateTo(Screen.Navigation)
  }
}

@ExperimentalFoundationApi
@Composable
fun MyList() {
  LazyColumn(
      contentPadding = PaddingValues(top = 30.dp, bottom = 60.dp),
      verticalArrangement = Arrangement.spacedBy(20.dp)
  ) {
    items(items = items) { item ->
      ListItem(bookCategory = item)
    }
  }
}

@ExperimentalFoundationApi //stickyHeader is experimental
@Composable
fun ListItem(bookCategory: BookCategory, modifier: Modifier = Modifier) {
  Column(
      verticalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    Text(
      text = stringResource(id = bookCategory.categoryResourceId),
      modifier = modifier.padding(start = 30.dp),
      fontSize = 22.sp,
      fontWeight = FontWeight.Bold,
      color = colorResource(id = R.color.colorPrimary),
    )
    LazyRow(
      modifier = modifier.height(260.dp),
      contentPadding = PaddingValues(start = 30.dp, end = 30.dp),
      horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      itemsIndexed(items = bookCategory.bookImageResources) { index, items ->
        NumberedBookImage(imageResId = items, index)
      }
//      stickyHeader {
//        Text(
//          text = stringResource(id = bookCategory.categoryResourceId),
//          modifier = modifier.padding(start = 30.dp),
//          fontSize = 22.sp,
//          fontWeight = FontWeight.Bold,
//          color = colorResource(id = R.color.colorPrimary),
//        )
//      }
    }
  }
}

@Composable
fun NumberedBookImage(@DrawableRes imageResId: Int, index: Int) {
  Box {
    Image(
      painter = painterResource(id = imageResId),
      modifier = Modifier.clip(RoundedCornerShape(10.dp)),
      contentScale = ContentScale.Fit,
    )
    Text(
      text = index.plus(1).toString(),
      fontSize = 36.sp,
      fontWeight = FontWeight.Black,
    )
  }
}
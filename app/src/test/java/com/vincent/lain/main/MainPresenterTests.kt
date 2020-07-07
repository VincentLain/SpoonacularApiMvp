/*
 * Copyright (c) 2018 Razeware LLC
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
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.vincent.lain.main

import com.vincent.lain.BaseTest
import com.vincent.lain.RxImmediateSchedulerRule
import com.vincent.lain.model.LocalDataSource
import com.vincent.lain.model.Menu
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.ArrayList

@RunWith(MockitoJUnitRunner::class)
class MainPresenterTests : BaseTest() {
    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    private lateinit var mockActivity: MainContract.ViewInterface

    @Mock
    private lateinit var mockDataSource: LocalDataSource

    lateinit var mainPresenter: MainPresenter

    @Before
    fun setUp() {
        mainPresenter = MainPresenter(viewInterface = mockActivity, dataSource = mockDataSource)
    }

    @Test
    fun testGetMyMoviesList() {
        //Set up
        val myDummyMovies = dummyAllMenus
        Mockito.doReturn(Observable.just(myDummyMovies)).`when`(mockDataSource).allMenus

        //Invoke
        mainPresenter.getMyMenusList()

        //Assert
        Mockito.verify(mockDataSource).allMenus
        Mockito.verify(mockActivity).displayMenus(myDummyMovies)
    }

    @Test
    fun tesGetMenuListWithNoMenus() {
        Mockito.doReturn(Observable.just(ArrayList<Menu>())).`when`(mockDataSource).allMenus

        mainPresenter.getMyMenusList()

        Mockito.verify(mockDataSource).allMenus
        Mockito.verify(mockActivity).displayNoMenus()
    }

  @Test
  fun testDeleteSingle() {
    val deletedHashSet = deletedHashSetSingle
    mainPresenter.onDelete(deletedHashSet)

    for (menu in deletedHashSet) {
      Mockito.verify(mockDataSource).delete(menu)
    }

    Mockito.verify(mockActivity).displayMessage("Menu deleted")
  }

    @Test
    fun testDeleteMultiple() {
        val deletedHashSet = deletedHashSetMultiple
        mainPresenter.onDelete(deletedHashSet)

        for (menu in deletedHashSet) {
            Mockito.verify(mockDataSource).delete(menu)
        }

        Mockito.verify(mockActivity).displayMessage("Menus deleted")
    }


    //Helper functions
    private val dummyAllMenus: ArrayList<Menu>
        get() {
            val dummyMenuList = ArrayList<Menu>()
            dummyMenuList.add(Menu("Title1", "Image1"))
            dummyMenuList.add(Menu("Title2", "Image2"))
            dummyMenuList.add(Menu("Title3", "Image3"))
            dummyMenuList.add(Menu("Title4", "Image4"))
            return dummyMenuList
        }

    private val deletedHashSetSingle: HashSet<Menu>
        get() {
            val deletedHashSet = HashSet<Menu>()
            deletedHashSet.add(dummyAllMenus[1])

            return deletedHashSet
        }

    private val deletedHashSetMultiple: HashSet<Menu>
        get() {
          val deletedHashSet = HashSet<Menu>()
          deletedHashSet.add(dummyAllMenus[1])
          deletedHashSet.add(dummyAllMenus[3])

          return deletedHashSet
        }
}

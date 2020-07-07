package com.vincent.lain.search

import com.vincent.lain.BaseTest
import com.vincent.lain.RxImmediateSchedulerRule
import com.vincent.lain.model.Menu
import com.vincent.lain.model.MenuResponse
import com.vincent.lain.model.RemoteDataSource
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SearchPresenterTests : BaseTest() {

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    private lateinit var mockActivity: SearchContract.ViewInterface

    @Mock
    private val mockDataSource = RemoteDataSource()

    lateinit var searchPresenter: SearchPresenter

    @Before
    fun setUp() {
        searchPresenter = SearchPresenter(viewInterface = mockActivity, dataSource = mockDataSource)
    }

    @Test
    fun testSearchMenu() {
        //Set up
        val myDummyResponse = dummyResponse
        Mockito.doReturn(Observable.just(myDummyResponse)).`when`(mockDataSource)
            .searchResultsObservable(anyString())

        //Invoke
        searchPresenter.getSearchResults("burger")

        //Assert
        Mockito.verify(mockActivity).displayResult(myDummyResponse)
    }

    @Test
    fun testSearchMenuError() {
        Mockito.doReturn(Observable.error<Throwable>(Throwable("Something went wrong")))
            .`when`(mockDataSource)
            .searchResultsObservable(anyString())

        searchPresenter.getSearchResults("burger")

        Mockito.verify(mockActivity).displayError("Error fetching Menus Data")
    }

    private val dummyResponse: MenuResponse
        get() {
            val dummyMenuList = ArrayList<Menu>()
            dummyMenuList.add(Menu("title1", "imagePatch1"))
            dummyMenuList.add(Menu("title2", "imagePatch2"))
            dummyMenuList.add(Menu("title3", "imagePatch3"))
            dummyMenuList.add(Menu("title4", "imagePatch4"))
            dummyMenuList.add(Menu("title5", "imagePatch5"))

            return MenuResponse(5, "menuItem", 0, 5, dummyMenuList)
        }
}

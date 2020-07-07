package com.vincent.lain.add

import com.vincent.lain.BaseTest
import com.vincent.lain.model.LocalDataSource
import com.vincent.lain.model.LocalDatabase
import com.vincent.lain.model.Menu
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AddMenuPresenterTests: BaseTest() {

    @Mock
    private lateinit var mockActivity: AddMenuContract.ViewInterface

    @Mock
    private lateinit var mockDataSource: LocalDataSource

    lateinit var addMenuPresenter: AddMenuPresenter

    @Captor
    private lateinit var menuArgumentCaptor: ArgumentCaptor<Menu>

    @Before
    fun setUp() {
        addMenuPresenter = AddMenuPresenter(viewInterface = mockActivity, dataSource = mockDataSource)
    }

    @Test
    fun testAddMenuNoTitle() {
        addMenuPresenter.addMenu("", "")

        Mockito.verify(mockActivity).displayError("Menu title cannot be empty")

    }

    @Test
    fun testAddMenuWithTitle() {
        addMenuPresenter.addMenu("Title", "ImagePath")

        Mockito.verify(mockDataSource).insert(captureArg(menuArgumentCaptor))
        assertEquals("Title", menuArgumentCaptor.value.title)
        Mockito.verify(mockActivity).returnToMain()
    }

}
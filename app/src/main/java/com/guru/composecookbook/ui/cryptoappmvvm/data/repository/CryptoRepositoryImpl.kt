package com.guru.composecookbook.ui.cryptoappmvvm.data.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.guru.composecookbook.ui.cryptoappmvvm.data.CryptoApiMapper
import com.guru.composecookbook.ui.cryptoappmvvm.data.api.CryptoApi
import com.guru.composecookbook.ui.cryptoappmvvm.data.db.daos.CryptoDao
import com.guru.composecookbook.ui.cryptoappmvvm.data.db.entities.Crypto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CryptoRepositoryImpl(
    private val cryptoApi: CryptoApi,
    private val cryptoDao: CryptoDao,
    private val cryptoApiMapper: CryptoApiMapper
) : CryptoRepository {

    @WorkerThread
    override suspend fun getAllCryptos() = flow {
        val response = cryptoApi.getAllCrypto()

        if (response.isSuccessful && !response.body().isNullOrEmpty()) {
            val cryptoApiResponseList = response.body()
            val cryptoList = cryptoApiResponseList?.map { cryptoApiResponse ->
                cryptoApiMapper.map(cryptoApiResponse)
            }
            emit(cryptoList ?: emptyList<Crypto>())
        } else {
            emit(error(response.message() ?: "Failed to load data"))
        }

    }.flowOn(Dispatchers.IO)

    override suspend fun getFavourite(): LiveData<List<Crypto>> = cryptoDao.getFavCryptos()

    override suspend fun addFavorite(crypto: Crypto) {
        cryptoDao.addFav(crypto)
    }

    override suspend fun removeFavorite(crypto: Crypto) {
        cryptoDao.removeFav(crypto)
    }

}
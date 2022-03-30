package com.example.testioasys2.data.repository

import com.example.testioasys2.data.EnterpriseStatus

interface EnterpriseRepository {
    fun getEnterprises(name: String?, enterpriseStatusCallBack: (enterpriseStatus: EnterpriseStatus) -> Unit)
}
package com.contextwallet.di

import com.contextwallet.data.remote.NominatimApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.dnsoverhttps.DnsOverHttps
import okhttp3.HttpUrl.Companion.toHttpUrl
import java.net.InetAddress

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideNominatimApi(): NominatimApi {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        
        // Configure DNS over HTTPS to bypass local emulator issues
        val appClient = OkHttpClient.Builder().build()
        val dns = DnsOverHttps.Builder()
            .client(appClient)
            .url("https://1.1.1.1/dns-query".toHttpUrl())
            .bootstrapDnsHosts(InetAddress.getByName("1.1.1.1"), InetAddress.getByName("1.0.0.1"))
            .build()
        
        val client = OkHttpClient.Builder()
            .dns(dns)
            .addInterceptor(logging)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .header("User-Agent", "DocumentButler-AndroidApp-v1.1 (github.com/sergiovalijeguiadanes/DocumentButler)")
                    .header("Accept", "application/json")
                    .header("Accept-Language", "es,en;q=0.9")
                    .header("Referer", "https://github.com/sergiovalijeguiadanes/DocumentButler")
                    .build()
                chain.proceed(request)
            }
            .build()
            
        return Retrofit.Builder()
            .baseUrl("https://nominatim.openstreetmap.org/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NominatimApi::class.java)
    }
}

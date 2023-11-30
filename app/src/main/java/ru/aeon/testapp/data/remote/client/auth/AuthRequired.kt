package ru.aeon.testapp.data.remote.client.auth

/**
 * Created by Roman on 09.07.2022
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
annotation class AuthRequired 
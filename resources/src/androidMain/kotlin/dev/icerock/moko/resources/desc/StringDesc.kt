/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.resources.desc

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import dev.icerock.moko.resources.PluralsResource
import dev.icerock.moko.resources.StringResource
import kotlinx.android.parcel.Parcelize

actual sealed class StringDesc {
    protected fun processArgs(args: List<Any>, context: Context): Array<out Any> {
        return args.toList().map { (it as? StringDesc)?.toString(context) ?: it }.toTypedArray()
    }

    @Parcelize
    actual data class Resource actual constructor(val stringRes: StringResource) : StringDesc(), Parcelable {
        override fun toString(context: Context): String {
            return context.getString(stringRes.resourceId)
        }
    }

    actual data class ResourceFormatted actual constructor(
        val stringRes: StringResource,
        val args: List<Any>
    ) : StringDesc() {
        override fun toString(context: Context): String {
            return context.getString(
                stringRes.resourceId, *processArgs(args, context)
            )
        }

        actual constructor(stringRes: StringResource, vararg args: Any) : this(
            stringRes,
            args.toList()
        )
    }

    @Parcelize
    actual data class Plural actual constructor(
        val pluralsRes: PluralsResource,
        val number: Int
    ) : StringDesc(), Parcelable {
        override fun toString(context: Context): String {
            return context.resources.getQuantityString(pluralsRes.resourceId, number)
        }
    }

    actual data class PluralFormatted actual constructor(
        val pluralsRes: PluralsResource,
        val number: Int,
        val args: List<Any>
    ) : StringDesc() {
        override fun toString(context: Context): String {
            return context.resources.getQuantityString(
                pluralsRes.resourceId,
                number,
                *processArgs(args, context)
            )
        }

        actual constructor(pluralsRes: PluralsResource, number: Int, vararg args: Any) : this(
            pluralsRes,
            number,
            args.toList()
        )
    }

    @Parcelize
    actual data class Raw actual constructor(
        val string: String
    ) : StringDesc(), Parcelable {
        override fun toString(context: Context): String {
            return string
        }
    }

    actual data class Composition actual constructor(val args: List<StringDesc>, val separator: String?) :
        StringDesc() {
        override fun toString(context: Context): String {
            return StringBuilder().apply {
                args.forEachIndexed { index, stringDesc ->
                    if (index != 0 && separator != null) {
                        append(separator)
                    }
                    append(stringDesc.toString(context))
                }
            }.toString()
        }
    }

    abstract fun toString(context: Context): String
}

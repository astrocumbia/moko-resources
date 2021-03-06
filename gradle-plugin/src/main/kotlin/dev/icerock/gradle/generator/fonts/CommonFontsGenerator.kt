/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.gradle.generator.fonts

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.KModifier
import org.gradle.api.file.FileTree
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

class CommonFontsGenerator(
    sourceSet: KotlinSourceSet,
    inputFileTree: FileTree
) : FontsGenerator(
    sourceSet = sourceSet,
    inputFileTree = inputFileTree
) {
    override fun getClassModifiers(): Array<KModifier> = emptyArray()

    override fun getPropertyModifiers(): Array<KModifier> = emptyArray()

    override fun getPropertyInitializer(key: String): CodeBlock? = null
}

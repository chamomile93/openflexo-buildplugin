package org.openflexo.buildplugin

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Config plugin for OpenFlexo projects Gradle build.
 * This file is not a Gradle build script (build.gradle).
 * It's a Groovy source file implementing a Gradle plugin, using the Gradle API.
 * Because it's ordinary Groovy source code, it belongs in a .groovy file, not a .gradle file.
 * Created by charlie on 24/01/2017.
 */
class OpenFlexoBuildConfig implements Plugin<Project> {

    void apply(Project project) {
        //println project.version
        def extension = '-SNAPSHOT'
        if (project.hasProperty('versionSuffix')) {
            extension = project.getProperty('versionSuffix')
        } else if (project.hasProperty('isRelease')) {
            if (project.getProperty('isRelease') == 'true' ||
                project.getProperty('isRelease') == 'yes' ||
                project.getProperty('isRelease') == 'oui' ||
                project.getProperty('isRelease') == 'vrai') {
                extension = ''
                }
        }
        project.version = project.version + extension
        project.subprojects.each { Project p ->    p.version = project.version }

        //println project.version

        // Beware connie version is used by the plugin to add the dependency to testutils
        // In a future version, we could put all versions in ext and use them in the plugin

        // TODO idf "ext", for extension and why ? is it related to "class OpenFlexoExtension" ?
        project.ext.connieVersion = '2.1.0' + extension

        project.apply plugin: 'org.openflexo.buildplugin'
        // TODO idf ".openflexo.utilsVersion" is possible because of ".apply ... buildplugin?"
        // TODO no need to type uilsVersion from "class OpenFlexoExtension" ?
        project.openflexo.utilsVersion = '1.7' + extension
        project.openflexo.connieVersion = project.ext.connieVersion
        project.openflexo.pamelaVersion = '1.6.1' + extension

        project.openflexo.ginaVersion = '2.4' + extension
        project.openflexo.dianaVersion = '1.7' + extension

        project.openflexo.openflexoVersion = '3.0' + extension
    }

}


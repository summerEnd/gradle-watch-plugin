package com.bluepapa32.gradle.plugins.watch

import com.bluepapa32.gradle.plugins.GradlePluginSpecification
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class WatchPluginSpec extends GradlePluginSpecification {

    def "applies plugin"() {
        when:
        apply plugin: 'watch'

        then:
        tasks['watch'] instanceof WatchTask
    }

    def "configures watch"() {
        setup:
        apply plugin: 'watch'

        when:
        watch {
            main {
                files fileTree(dir: '/src/main/java', include: '**.java')
                tasks 'foo', 'bar'
            }
            test {
                files fileTree(dir: '/src/test/java', include: '**.java')
                tasks 'hoge', 'boo'
            }
        }

        then:
        tasks['watch'] instanceof WatchTask
        tasks['watch'].targets.size() == 2

        then:
        tasks['watchMain'] instanceof WatchTask
        tasks['watchMain'].targets*.name == ['main']

        then:
        tasks['watchTest'] instanceof WatchTask
        tasks['watchTest'].targets*.name == ['test']

        then:
        tasks.findByPath('watchmain') == null
        tasks.findByPath('watchtest') == null
        tasks.findByPath('watchHoge') == null
    }
}

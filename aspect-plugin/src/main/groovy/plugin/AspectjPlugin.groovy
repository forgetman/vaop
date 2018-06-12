package plugin

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project

class AspectjPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.plugins.apply("android-aspectjx")

        final def log = project.logger
        log.error "========================"
        log.error "Aspectj切片开始编织Class!"
        log.error "========================"

        def hasApp = project.plugins.withType(AppPlugin)
        def hasLib = project.plugins.withType(LibraryPlugin)
        final def variants
        if (hasApp) {
            variants = project.android.applicationVariants
        } else {
            variants = project.android.libraryVariants
        }

        variants.all { variant ->
            def javaCompile = variant.javaCompile
            javaCompile.doLast {
                String[] args = ["-showWeaveInfo",
                                 "-1.8",
                                 "-inpath", javaCompile.destinationDir.toString(),
                                 "-aspectpath", javaCompile.classpath.asPath,
                                 "-d", javaCompile.destinationDir.toString(),
                                 "-classpath", javaCompile.classpath.asPath,
                                 "-bootclasspath", project.android.bootClasspath.join(File.pathSeparator)]
                log.debug "ajc args: " + Arrays.toString(args)
            }
        }
    }

}
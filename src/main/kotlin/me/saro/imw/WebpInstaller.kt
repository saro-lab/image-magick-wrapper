package me.saro.imw

import java.io.File
import java.net.URI
import java.nio.file.*
import kotlin.io.path.name

class WebpInstaller{
    companion object {

        fun install(path: String) {
            copyBinFiles(platform, path)
        }

        private val name get() = System.getProperty("os.name").lowercase()
        private val arch get() = System.getProperty("os.arch").lowercase()

        private val platform get() = when {
            // linux
            name.contains("linux") && arch.contains("64") -> when {
                arch.contains("aarch64") -> "linux-aarch64"
                else -> "linux-x86-64"
            }

            // mac
            name.contains("mac") && arch.contains("64") ->  when {
                arch.contains("arm") -> "mac-arm64"
                else -> "mac-x86-64"
            }

            // windows
            name.contains("win") && arch.contains("64") -> "windows-x64"

            // not supported
            else -> throw RuntimeException("not supported: $name $arch")
        }

        private fun loadDirectory(path: String): File {
            val toPathRoot = File(path)
            !toPathRoot.exists() && toPathRoot.mkdirs()
            if (!toPathRoot.isDirectory) {
                throw RuntimeException("${toPathRoot.absolutePath} is not directory")
            } else if (!toPathRoot.canWrite()) {
                throw RuntimeException("${toPathRoot.absolutePath} is not writable (permission denied)")
            }
            return toPathRoot
        }

        private fun copyBinFiles(platform: String, toPath: String) {
            val toPathRoot = loadDirectory(toPath)
            val jarPath = Companion::class.java.protectionDomain.codeSource.location.toURI().path

            if (jarPath.endsWith(".jar")) { // in jar
                FileSystems.newFileSystem(URI.create("jar:file:$jarPath"), mutableMapOf<String, Any>()).use {
                    Files
                        .walk(it.getPath(platform))
                        .forEach { file -> copyWithPermission(file, File(toPathRoot, file.name)) }
                }
            } else { // in ide
                File(File(Companion::class.java.getResource("/$platform")!!.toURI()).absolutePath)
                    .listFiles()!!
                    .forEach { file -> copyWithPermission(file.toPath(), File(toPathRoot, file.name)) }
            }
        }

        private fun copyWithPermission(from: Path, to: File) {
            Files.copy(from, to.toPath(), StandardCopyOption.REPLACE_EXISTING)
            to.setWritable(true)
            to.setExecutable(true)
        }
    }
}

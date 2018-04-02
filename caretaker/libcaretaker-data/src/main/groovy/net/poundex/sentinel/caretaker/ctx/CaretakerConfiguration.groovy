package net.poundex.sentinel.caretaker.ctx

import groovy.transform.CompileStatic
import net.poundex.sentinel.caretaker.home.Monitor
import org.grails.datastore.gorm.GormEntity
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

@Configuration
@CompileStatic
//@Slf4j
class CaretakerConfiguration
{
	private static Logger log = LoggerFactory.getLogger(CaretakerConfiguration)
	private static final ThreadGroup threadGroup = new ThreadGroup("CaretakerWorker")

	@Bean("caretakerWorkerPool")
	ThreadPoolExecutor workerPool()
	{
		return new ThreadPoolExecutor(4, 8, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(),
				{ Runnable r ->
					return new Thread(threadGroup, {
						Monitor.withNewSession {
							Monitor.withNewTransaction {
								r.run()
							}
						}
					}).with {
						uncaughtExceptionHandler = { Thread t, Throwable ex ->
							log.error("Thread ${t.name} did not handle error", ex)
						}
						daemon = false
						priority = MAX_PRIORITY
						return it
					}
				},
				{ r, pool ->
					log.error("Executor ${pool} rejected job ${r}")
				})
	}
}

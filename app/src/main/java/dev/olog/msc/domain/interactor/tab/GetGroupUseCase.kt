package dev.olog.msc.domain.interactor.tab

import dev.olog.msc.domain.executors.ComputationScheduler
import dev.olog.msc.domain.gateway.BaseGateway
import dev.olog.msc.domain.interactor.base.ObservableUseCase
import io.reactivex.Observable

abstract class GetGroupUseCase<T>(
        private val gateway: BaseGateway<T, *>,
        schedulers: ComputationScheduler
) : ObservableUseCase<List<T>>(schedulers) {


    override fun buildUseCaseObservable(): Observable<List<T>> = gateway.getAll()
}
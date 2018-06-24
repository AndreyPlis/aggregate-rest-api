package com.andreyplis.aggregaterestapi.service.remote

import com.andreyplis.aggregaterestapi.model.Context
import com.andreyplis.aggregaterestapi.model.Function
import com.andreyplis.aggregaterestapi.model.Variable
import com.andreyplis.aggregaterestapi.service.ServerService
import com.tibbo.aggregate.common.datatable.encoding.JsonEncodingHelper
import org.springframework.stereotype.Service
import java.util.*

@Service
internal class RemoteServerService(val remoteAggreGate: RemoteAggreGate) : ServerService {
    override fun getContextVariable(path: String, id: String): Optional<String> {
        val context = remoteAggreGate.contextManager.get(path) ?: return Optional.empty()

        return try {
            Optional.of(JsonEncodingHelper.tableToJson(context.getVariable(id)))
        } catch (e: Exception) {
            Optional.empty()
        }
    }

    override fun getContext(path: String): Optional<Context> {
        val context = remoteAggreGate.contextManager.get(path) ?: return Optional.empty()
        val children = context.children.map { context2 -> Context(context2.path, context2.name, context2.description) }.toList()
        val variables = context.variableDefinitions.map { variable -> Variable(variable.name, variable.description) }.toList()
        val functions = context.functionDefinitions.map { function -> Function(function.name, function.description) }.toList()
        return Optional.of(Context(context.path, context.name, context.description, children, variables, functions))
    }


    override fun getAllContexts(): List<Context> {
        return remoteAggreGate.contextManager.root.children.map { context -> Context(context.path, context.name, context.description) }.toList()
    }

}
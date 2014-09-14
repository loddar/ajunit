/*
 * ajUnit - Unit Testing AspectJ.
 *
 * Copyright (C) 2013-2014 Marko Umek (http://fail-early.com/contact)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package org.failearly.ajunit.internal.runner;

import org.failearly.ajunit.internal.util.MessageBuilder;
import org.failearly.ajunit.internal.util.MessageBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * TestResultCollectorImpl is responsible for ...
 */
final class TestResultCollectorImpl implements TestResultCollector {
    private boolean noJoinPointsSelected=false;
    private boolean missingNoneSelected=false;
    private int numFailures=0;
    private final List<String> selectedButNotApplied=new ArrayList<>();
    private final List<String> noneSelectedButApplied=new ArrayList<>();
    private final List<String> suppressedButApplied =new ArrayList<>();

    @Override
    public void noJoinPointsSelected() {
        numFailures++;
        noJoinPointsSelected = true;
    }

    @Override
    public void missingNoneSelected() {
        numFailures++;
        missingNoneSelected=true;
    }

    @Override
    public void selectedButNotApplied(String selectJoinPoint) {
        numFailures++;
        selectedButNotApplied.add(selectJoinPoint);
    }

    @Override
    public void noneSelectedButApplied(String appliedJoinPoint) {
        numFailures++;
        noneSelectedButApplied.add(appliedJoinPoint);
    }

    @Override
    public void suppressedButApplied(String suppressedJoinPoint) {
        numFailures++;
        suppressedButApplied.add(suppressedJoinPoint);
    }


    void onFailure(FailureHandler failureHandler) {
        if(numFailures>0) {
            failureHandler.doFail(doCreateFailureMessage());
        }
    }

    private String doCreateFailureMessage() {
        final MessageBuilder messageBuilder = buildFailingMessageHeader();
        buildNoJoinPointSelectedMessagePart(messageBuilder);
        buildMissingNoneSelectedMessagePart(messageBuilder);
        buildSelectedButNotAppliedMessagePart(messageBuilder);
        buildNoneSelectedButAppliedMessagePart(messageBuilder);
        buildSuppressedButAppliedMessagePart(messageBuilder);

        return messageBuilder.build();
    }

    private void buildSuppressedButAppliedMessagePart(MessageBuilder messageBuilder) {
        if( ! suppressedButApplied.isEmpty() ) {
            messageBuilder.newline().line("").arg(suppressedButApplied.size()).part("suppressed join point(s) has/have been applied. The join point(s):");
            listJoinPoints(messageBuilder, suppressedButApplied);
            messageBuilder.line("Possible reasons for this error:")
                    .subLine("Your pointcut definition should not select the suppressed join point(s). Your pointcut definition is too weak.")
                    .subLine("The suppressed join point(s) should be part of selectable join points. " +
                            "Enable suppressed join point(s) by AjUnitSetup.enableSuppressedJoinPoints()");
        }
    }

    private void buildNoneSelectedButAppliedMessagePart(MessageBuilder messageBuilder) {
        if( ! noneSelectedButApplied.isEmpty() ) {
            messageBuilder.newline().line("").arg(noneSelectedButApplied.size())
                    .part("none(!) selected join point(s) has/have been applied. The join point(s):");
            listJoinPoints(messageBuilder, noneSelectedButApplied);
            messageBuilder.line("Possible reasons for this error:")
                    .subLine("Your pointcut definition should not select the listed join point(s). Your pointcut definition is too weak.")
                    .subLine("And of course it's possible that your join point selector isn't correct.");
        }
    }

    private void buildSelectedButNotAppliedMessagePart(MessageBuilder messageBuilder) {
        if( ! selectedButNotApplied.isEmpty() ) {
            messageBuilder.newline().line("").arg(selectedButNotApplied.size()).part("selected join point(s) has/have not(!) been applied. The join point(s):");
            listJoinPoints(messageBuilder, selectedButNotApplied);
            messageBuilder.line("Possible reasons for this error:")
                    .subLine("You did not call/execute the listed join point(s). Adapt execute() accordingly.")
                    .subLine("Your pointcut definition does not select the listed join point(s). Your pointcut definition is too strong.")
                    .subLine("And of course it's possible that your join point selector isn't correct.");
        }
    }

    private void buildMissingNoneSelectedMessagePart(MessageBuilder messageBuilder) {
        if(missingNoneSelected) {
            messageBuilder.newline().line("Missing none selected join points. Possible reason:")
                .subLine("Your ajUnit universe is too small. For example: Add a new method which should not selected by your pointcut definition.");
        }
    }

    private MessageBuilder buildFailingMessageHeader() {
        return MessageBuilders.message("Pointcut test failed with")
                    .arg(numFailures).part("error(s).").newline().newline("Details:");
    }

    private void buildNoJoinPointSelectedMessagePart(MessageBuilder messageBuilder) {
        if(noJoinPointsSelected) {
            messageBuilder.newline().line("No join points selected. Possible reasons:")
                .subLine("Method assertPointcut(JoinPointSelector) uses notYetSpecified().")
                .subLine("You defined a join point selector which has a impossible condition.");
        }
    }

    private static void listJoinPoints(MessageBuilder messageBuilder, List<String> joinPointList) {
        Collections.sort(joinPointList);
        for (String joinPoint : joinPointList) {
            messageBuilder.subLine(joinPoint);
        }
    }
}

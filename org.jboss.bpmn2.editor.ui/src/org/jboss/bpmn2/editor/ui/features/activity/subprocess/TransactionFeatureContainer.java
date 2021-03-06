/******************************************************************************* 
 * Copyright (c) 2011 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/
package org.jboss.bpmn2.editor.ui.features.activity.subprocess;

import org.eclipse.bpmn2.SubProcess;
import org.eclipse.bpmn2.Transaction;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.ILayoutFeature;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.mm.algorithms.RoundedRectangle;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.jboss.bpmn2.editor.core.ModelHandler;
import org.jboss.bpmn2.editor.core.features.activity.subprocess.AbstractCreateSubProcess;
import org.jboss.bpmn2.editor.core.utils.StyleUtil;
import org.jboss.bpmn2.editor.ui.ImageProvider;

public class TransactionFeatureContainer extends AbstractSubProcessFeatureContainer {

	private static final int offset = 3;

	@Override
	public boolean canApplyTo(Object o) {
		return super.canApplyTo(o) && o instanceof Transaction;
	}

	@Override
	public ICreateFeature getCreateFeature(IFeatureProvider fp) {
		return new CreateTransactionFeature(fp);
	}

	@Override
	public IAddFeature getAddFeature(IFeatureProvider fp) {
		return new AddExpandedSubProcessFeature(fp) {

			@Override
			protected void decorateActivityRectangle(RoundedRectangle rect) {
				IGaService gaService = Graphiti.getGaService();
				RoundedRectangle innerRect = gaService.createRoundedRectangle(rect, 5, 5);
				innerRect.setFilled(false);
				innerRect.setForeground(manageColor(StyleUtil.CLASS_FOREGROUND));
				gaService.setLocationAndSize(innerRect, offset, offset, rect.getWidth() - (2 * offset),
						rect.getHeight() - (2 * offset));
			}

			@Override
			protected int getMarkerContainerOffset() {
				return offset;
			}
		};
	}

	@Override
	public ILayoutFeature getLayoutFeature(IFeatureProvider fp) {
		return new SubProcessLayoutFeature(fp) {
			@Override
			protected void layoutInRectangle(RoundedRectangle rect) {
				IGaService gaService = Graphiti.getGaService();
				RoundedRectangle innerRect = (RoundedRectangle) rect.getGraphicsAlgorithmChildren().get(0);
				gaService.setSize(innerRect, rect.getWidth() - 6, rect.getHeight() - 6);
			}

			@Override
			protected int getMarkerContainerOffset() {
				return offset;
			}
		};
	}

	public static class CreateTransactionFeature extends AbstractCreateSubProcess {

		public CreateTransactionFeature(IFeatureProvider fp) {
			super(fp, "Expanded Transaction",
					"Specialized type of sub-process that will have behavior controlled by transaction protocol");
		}

		@Override
		protected SubProcess createFlowElement(ICreateContext context) {
			SubProcess subProcess = ModelHandler.FACTORY.createTransaction();
			subProcess.setName("Transaction");
			return subProcess;
		}

		@Override
		protected String getStencilImageId() {
			return ImageProvider.IMG_16_TRANSACTION;
		}
	}
}
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
package org.jboss.bpmn2.editor.ui.features.flow;

import org.eclipse.bpmn2.Association;
import org.eclipse.bpmn2.BaseElement;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateConnectionFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.styles.LineStyle;
import org.jboss.bpmn2.editor.core.ModelHandler;
import org.jboss.bpmn2.editor.core.features.BaseElementConnectionFeatureContainer;
import org.jboss.bpmn2.editor.core.features.flow.AbstractAddFlowFeature;
import org.jboss.bpmn2.editor.core.features.flow.AbstractCreateFlowFeature;
import org.jboss.bpmn2.editor.ui.ImageProvider;

public class AssociationFeatureContainer extends BaseElementConnectionFeatureContainer {

	@Override
	public boolean canApplyTo(Object o) {
		return super.canApplyTo(o) && o instanceof Association;
	}

	@Override
	public IAddFeature getAddFeature(IFeatureProvider fp) {
		return new AbstractAddFlowFeature(fp) {

			@Override
			protected void decorateConnectionLine(Polyline connectionLine) {
				connectionLine.setLineWidth(2);
				connectionLine.setLineStyle(LineStyle.DOT);
			}

			@Override
			protected Class<? extends BaseElement> getBoClass() {
				return Association.class;
			}
		};
	}

	@Override
	public ICreateConnectionFeature getCreateConnectionFeature(IFeatureProvider fp) {
		return new CreateAssociationFeature(fp);
	}

	public static class CreateAssociationFeature extends AbstractCreateFlowFeature<BaseElement, BaseElement> {

		public CreateAssociationFeature(IFeatureProvider fp) {
			super(fp, "Association", "Associate information with artifacts and flow objects");
		}

		@Override
		protected String getStencilImageId() {
			return ImageProvider.IMG_16_ASSOCIATION;
		}

		@Override
		protected Class<BaseElement> getSourceClass() {
			return BaseElement.class;
		}

		@Override
		protected Class<BaseElement> getTargetClass() {
			return BaseElement.class;
		}

		@Override
		protected BaseElement createFlow(ModelHandler mh, BaseElement source, BaseElement target) {
			return mh.createAssociation(source, target);
		}
	}
}
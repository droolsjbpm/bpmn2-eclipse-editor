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

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.DataOutputAssociation;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateConnectionFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.styles.LineStyle;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ConnectionDecorator;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeService;
import org.jboss.bpmn2.editor.core.features.BaseElementConnectionFeatureContainer;
import org.jboss.bpmn2.editor.core.features.flow.AbstractAddFlowFeature;
import org.jboss.bpmn2.editor.core.utils.StyleUtil;

public class DataOutputAssociationFeatureContainer extends BaseElementConnectionFeatureContainer {

	@Override
	public boolean canApplyTo(Object o) {
		return super.canApplyTo(o) && o instanceof DataOutputAssociation;
	}

	@Override
	public IAddFeature getAddFeature(IFeatureProvider fp) {
		return new AbstractAddFlowFeature(fp) {

			@Override
			protected void decorateConnectionLine(Polyline connectionLine) {
				connectionLine.setLineStyle(LineStyle.DOT);
			}

			@Override
			protected void createConnectionDecorators(Connection connection) {
				IPeService peService = Graphiti.getPeService();
				IGaService gaService = Graphiti.getGaService();

				ConnectionDecorator endDecorator = peService.createConnectionDecorator(connection, false, 1.0, true);

				int w = 5;
				int l = 10;

				Polyline polyline = gaService.createPolyline(endDecorator, new int[] { -l, w, 0, 0, -l, -w });
				polyline.setForeground(manageColor(StyleUtil.CLASS_FOREGROUND));
				polyline.setLineWidth(1);
			}

			@Override
			protected Class<? extends BaseElement> getBoClass() {
				return DataOutputAssociation.class;
			}
		};
	}

	@Override
	public ICreateConnectionFeature getCreateConnectionFeature(IFeatureProvider fp) {
		return null;
	}
}
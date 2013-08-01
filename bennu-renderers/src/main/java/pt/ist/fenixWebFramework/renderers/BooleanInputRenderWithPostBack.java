package pt.ist.fenixWebFramework.renderers;

import pt.ist.fenixWebFramework.renderers.components.HtmlCheckBox;
import pt.ist.fenixWebFramework.renderers.components.HtmlComponent;
import pt.ist.fenixWebFramework.renderers.components.HtmlHiddenField;
import pt.ist.fenixWebFramework.renderers.components.HtmlInlineContainer;
import pt.ist.fenixWebFramework.renderers.components.controllers.HtmlController;
import pt.ist.fenixWebFramework.renderers.components.state.IViewState;
import pt.ist.fenixWebFramework.renderers.components.state.ViewDestination;
import pt.ist.fenixWebFramework.renderers.layouts.Layout;
import pt.ist.fenixWebFramework.renderers.model.MetaSlot;

public class BooleanInputRenderWithPostBack extends BooleanInputRenderer {
    private String destination;
    private final String HIDDEN_NAME = "postback";

    public String getDestination() {
        return destination;
    }

    /**
     * Allows to choose the postback destination. If this property is not
     * specified the default "postback" destination is used.
     * 
     * @property
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Override
    protected Layout getLayout(Object object, Class type) {
        final Layout layout = super.getLayout(object, type);

        return new Layout() {

            @Override
            public HtmlComponent createComponent(Object object, Class type) {
                HtmlInlineContainer container = new HtmlInlineContainer();

                String prefix =
                        HtmlComponent.getValidIdOrName(((MetaSlot) getInputContext().getMetaObject()).getKey().toString()
                                .replaceAll("\\.", "_").replaceAll("\\:", "_"));

                HtmlHiddenField hidden = new HtmlHiddenField(prefix + HIDDEN_NAME, "");
                HtmlCheckBox checkBox = (HtmlCheckBox) layout.createComponent(object, type);

                checkBox.setOnClick("this.form." + prefix + HIDDEN_NAME + ".value='true';this.form.submit();");
                checkBox.setOnDblClick("this.form." + prefix + HIDDEN_NAME + ".value='true';this.form.submit();");
                checkBox.setController(new PostBackController(hidden, getDestination()));

                container.addChild(hidden);
                container.addChild(checkBox);

                return container;

            }

        };
    }

    private static class PostBackController extends HtmlController {

        private final HtmlHiddenField hidden;

        private final String destination;

        public PostBackController(HtmlHiddenField hidden, String destination) {
            this.hidden = hidden;
            this.destination = destination;
        }

        @Override
        public void execute(IViewState viewState) {
            if (hidden.getValue() != null && hidden.getValue().equalsIgnoreCase("true")) {
                String destinationName = this.destination == null ? "postBack" : this.destination;
                ViewDestination destination = viewState.getDestination(destinationName);

                if (destination != null) {
                    viewState.setCurrentDestination(destination);
                } else {
                    viewState.setCurrentDestination("postBack");
                }
                hidden.setValue("false");
                viewState.setSkipValidation(true);
            }
        }

    }
}
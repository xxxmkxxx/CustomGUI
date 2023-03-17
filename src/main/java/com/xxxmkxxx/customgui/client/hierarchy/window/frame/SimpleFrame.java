package com.xxxmkxxx.customgui.client.hierarchy.window.frame;

import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import com.xxxmkxxx.customgui.client.ui.controls.image.SimpleImage;
import lombok.Getter;

@Getter
public class SimpleFrame extends AbstractFrame {
    protected SimpleFrame(Pos startPos, Pos stopPos) {
        super(startPos, stopPos);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Pos startPos;
        private Pos stopPos;
        private float widthPercent;
        private float heightPercent;

        public Builder() {
            this.startPos = Pos.defaultPos();
            this.widthPercent = 2;
            this.heightPercent = 4;
        }

        public Builder startPos(Pos startPos) {
            try {
                this.startPos = (Pos) startPos.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
            return this;
        }

        public Builder stopPos(Pos stopPos) {
            try {
                this.stopPos = (Pos) stopPos.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
            return this;
        }

        public Builder positions(Pos startPos, Pos stopPos) {
            startPos(startPos);
            stopPos(stopPos);
            return this;
        }

        public Builder positions(AbstractFrame frame) {
            startPos(frame.getStartPos());
            stopPos(frame.getStopPos());
            return this;
        }

        public Builder widthPercent(float widthPercent) {
            this.widthPercent = widthPercent;
            return this;
        }

        public Builder heightPercent(float heightPercent) {
            this.heightPercent = heightPercent;
            return this;
        }

        public SimpleFrame build() {
            Pos stopPos = this.stopPos == null
                    ? Pos.builder()
                        .relativeCoords(
                                startPos.getXIndentPercent() + widthPercent,
                                startPos.getYIndentPercent() + heightPercent
                        )
                        .build(startPos.getXPercentValue(), startPos.getYPercentValue())
                    : this.stopPos;

            return new SimpleFrame(startPos, stopPos);
        }
    }
}